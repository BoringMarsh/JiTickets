package cn.edu.tongji.springbackend.controller;

import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.exceptions.LoginException;
import cn.edu.tongji.springbackend.service.ProfileService;
import cn.edu.tongji.springbackend.service.LoginService;
import cn.edu.tongji.springbackend.service.RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(KeywordsController.class);
    @Autowired
    private RegisterService registerService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private ProfileService profileService;

    @PostMapping("/register/student")
    public ResponseEntity<?> registerStudent(@RequestBody RegStudentRequest registrationRequest) {
        try {
            logger.info("start to register student");
            logger.info("Successfully received request: {}", registrationRequest);
            int user_id = registerService.registerStudent(registrationRequest);
            logger.info("Successfully registered");
            return new ResponseEntity<>(Map.of("message", "success", "user_ID", user_id), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "failed to register student"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register/society")
    public ResponseEntity<?> registerSociety(@RequestBody RegSocietyRequest registrationRequest) {
        try {
            logger.info("start to register society");
            logger.info("Successfully received request: {}", registrationRequest);
            registerService.registerSociety(registrationRequest);
            return new ResponseEntity<>("Society registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to register society: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    @GetMapping("/register/authentication/getUnauthSocieties")
    public ResponseEntity<Page<Society>> getUnauthSocieties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            // Assuming you have a method in SocietyService to fetch unauthenticated societies
            Page<Society> unauthSocieties = societyService.getUnauthenticatedSocieties(page, pageSize);

            return new ResponseEntity<>(unauthSocieties, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    */

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            logger.info("Successfully received request: {}", loginRequest);
            LoginResponse loginResponse = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (LoginException e) {
            // Handle login-related exceptions (e.g., account not found, incorrect password)
            return new ResponseEntity<>("Login failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            // Handle other exceptions (e.g., database connection issues)
            return new ResponseEntity<>("Login failed: Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profile/student/get")
    public ResponseEntity<?> getStudentProfile(@RequestParam String username) {
        try {
            logger.info("start to get student profile");
            logger.info("Successfully received request: {}", username);
            GetStudentProfileResponse studentProfile = profileService.getStudentProfile(username);
            if (studentProfile != null) {
                return new ResponseEntity<>(studentProfile, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve student profile: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/profile/student/modify")
    public ResponseEntity<?> modifyStudentProfile(@RequestBody ModifyStuProfileReq modifyRequest) {
        try {
            logger.info("start to modify student profile");
            logger.info("Successfully received request: {}", modifyRequest);
            profileService.modifyStudentProfile(modifyRequest);
            return new ResponseEntity<>("Student profile updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update student profile: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profile/society/info/get")
    public ResponseEntity<?> getSocietyProfileInfo(@RequestParam String username) {
        try {
            logger.info("start to get society profile");
            logger.info("Successfully received request: {}", username);
            // Fetch student information using the provided username
            GetSocietyProfileResponse societyProfile = profileService.getSocietyProfileInfo(username);
            if (societyProfile != null) {
                return new ResponseEntity<>(societyProfile, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Society not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve society profile: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/profile/society/modify")
    public ResponseEntity<?> modifySocietyProfile(@RequestBody ModifySocProfileReq modifyRequest) {
        try {
            logger.info("start to get society profile");
            logger.info("Successfully received request: {}", modifyRequest);
            // Update student information using the provided username and updateRequest
            profileService.modifySocietyProfile(modifyRequest);

            return new ResponseEntity<>("Student profile updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update student profile: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
