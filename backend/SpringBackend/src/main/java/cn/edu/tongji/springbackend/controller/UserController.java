package cn.edu.tongji.springbackend.controller;

import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.model.User;
import cn.edu.tongji.springbackend.mapper.UserMapper;
import cn.edu.tongji.springbackend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(KeywordsController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/register/student")
    public ResponseEntity<?> registerStudent(@RequestBody RegStudentRequest registrationRequest) {
        try {
            logger.info("start to register student");
            logger.info("Successfully received request: {}", registrationRequest);
            userService.registerStudent(registrationRequest);
            logger.info("Successfully registered");
            return new ResponseEntity<>("Student registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to register student: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register/society")
    public ResponseEntity<?> registerSociety(@RequestBody RegSocietyRequest registrationRequest) {
        try {
            logger.info("start to register society");
            logger.info("Successfully received request: {}", registrationRequest);
            userService.registerSociety(registrationRequest);
            return new ResponseEntity<>("Society registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to register society: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
            // Implement login logic and return appropriate response

            return new ResponseEntity<>(new LoginResponse("Login successful", HttpStatus.OK), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new LoginResponse("Login failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @GetMapping("/profile/student/get")
    public ResponseEntity<?> getStudentProfile(@RequestParam String username) {
        try {
            // Fetch student information using the provided username
            GetStudentProfileResponse studentProfile = userService.getStudentProfile(username);

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
    public ResponseEntity<?> modifyStudentProfile(@RequestParam String username, @RequestBody ModifyStuProfileReq modifyRequest) {
        try {
            // Perform validation on updateRequest if needed

            // Update student information using the provided username and updateRequest
            userService.modifyStudentProfile(username, modifyRequest);

            return new ResponseEntity<>("Student profile updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update student profile: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
