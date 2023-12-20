package cn.edu.tongji.springbackend.service.impl;

import cn.edu.tongji.springbackend.controller.KeywordsController;
import cn.edu.tongji.springbackend.dto.GetStudentProfileResponse;
import cn.edu.tongji.springbackend.dto.ModifyStuProfileReq;
import cn.edu.tongji.springbackend.dto.RegSocietyRequest;
import cn.edu.tongji.springbackend.dto.RegStudentRequest;
import cn.edu.tongji.springbackend.mapper.SocietyMapper;
import cn.edu.tongji.springbackend.mapper.StudentKeywordMapper;
import cn.edu.tongji.springbackend.mapper.StudentMapper;
import cn.edu.tongji.springbackend.mapper.UserMapper;
import cn.edu.tongji.springbackend.model.*;
import cn.edu.tongji.springbackend.service.UserService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private StudentKeywordMapper studentKeywordMapper;
    @Resource
    private SocietyMapper societyMapper;
    private static final Logger logger = LoggerFactory.getLogger(KeywordsController.class);
    @Override
    @Transactional
    public void registerStudent(RegStudentRequest registrationRequest) {

        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(registrationRequest.getPassword());
        user.setEmail(registrationRequest.getEmail());
        user.setPhone(registrationRequest.getPhone());
        user.setCampus(registrationRequest.getCampus());
        user.setLoginStatus(0);
        user.setAccountStatus(1);
        user.setBalance(0.0);
        user.setPayPassword(registrationRequest.getPayPassword());
        user.setRegTime(LocalDateTime.now());  // Set regTime to current time
        user.setRole(0);  // Assuming 0 is the role for normal users
        logger.info("Successfully set user info: {}", user);
        // Insert the user into the user table and retrieve the generated ID
        int rowsAffected = userMapper.insertUser(user);

        // Check if the insertion was successful
        if (rowsAffected > 0) {
            // Retrieve the generated ID and update the User object
            Integer generatedId = user.getId(); // Assuming the generated ID is in the getId() method
            user.setId(generatedId);
            logger.info("Successfully inserted user with ID: {}", generatedId);
        } else {
            logger.error("Failed to insert user");
        }

        Student student = new Student();
        student.setStuId(user.getId());
        student.setStuName(registrationRequest.getStuName());
        student.setStuYear(registrationRequest.getStuYear());
        student.setStuSchool(registrationRequest.getStuSchool());
        student.setStuMajor(registrationRequest.getStuMajor());
        student.setStuNotes(registrationRequest.getStuNotes());
        logger.info("Successfully set user info: {}", student);
        // Insert the student into the student table
        studentMapper.insertStudent(student);

        List<StudentKeyword> studentKeywords = registrationRequest.getKeywords().stream()
                .map(keyword -> {
                    StudentKeyword studentKeyword = new StudentKeyword();
                    studentKeyword.setStuId(user.getId());
                    studentKeyword.setKeyword(keyword);
                    studentKeyword.setPreferWeight(0.0);
                    return studentKeyword;
                })
                .collect(Collectors.toList());

        // Insert each student keyword into the student_keyword table
        for (StudentKeyword studentKeyword : studentKeywords) {
            studentKeywordMapper.insertStudentKeyword(studentKeyword);
        }
    }

    // Add other methods as needed
    @Transactional
    public void registerSociety(RegSocietyRequest registrationRequest) {
        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(registrationRequest.getPassword());
        user.setEmail(registrationRequest.getEmail());
        user.setPhone(registrationRequest.getPhone());
        user.setCampus(registrationRequest.getCampus());
        user.setLoginStatus(0);
        user.setAccountStatus(2);
        user.setBalance(0.0);
        user.setPayPassword(registrationRequest.getPayPassword());
        user.setRegTime(LocalDateTime.now());  // Set regTime to current time
        user.setRole(1);
        logger.info("Successfully set user info: {}", user);
        // Insert the user into the user table and retrieve the generated ID
        int rowsAffected = userMapper.insertUser(user);

        // Check if the insertion was successful
        if (rowsAffected > 0) {
            // Retrieve the generated ID and update the User object
            Integer generatedId = user.getId(); // Assuming the generated ID is in the getId() method
            user.setId(generatedId);
            logger.info("Successfully inserted user with ID: {}", generatedId);
        } else {
            logger.error("Failed to insert user");
        }

        Society society = new Society();
        society.setSocId(user.getId());
        society.setSocName(registrationRequest.getSocName());
        society.setSocIntro(registrationRequest.getSocIntro());
        society.setSocType(registrationRequest.getSocType());

        // Process and save society logo
        if (registrationRequest.getSocLogoFile() != null) {
            String logoPath = saveLogo(registrationRequest.getSocLogoFile());
            society.setSocLogo(logoPath);
        }

        // Insert the society into the society table
        societyMapper.insertSociety(society);

        // Process and save society admins, images, and keywords
        processSocietyAdmins(user.getId(), registrationRequest.getSocAdmins());
        processSocietyImages(user.getId(), registrationRequest.getSocImageFiles());
        processSocietyKeywords(user.getId(), registrationRequest.getSocKeywords());
    }

    // Helper methods for processing society admins, images, and keywords
    private void processSocietyAdmins(Integer socId, List<SocietyAdmin> socAdmins) {
        if (socAdmins != null) {
            for (SocietyAdmin admin : socAdmins) {
                admin.setSocId(socId);
                societyMapper.insertSocietyAdmin(admin);
            }
        }
    }

    private void processSocietyImages(Integer socId, List<MultipartFile> socImages) {
        if (socImages != null) {
            for (MultipartFile image : socImages) {
                String imagePath = saveImage(image);
                SocietyImage societyImage = new SocietyImage();
                societyImage.setSocId(socId);
                societyImage.setSocImage(imagePath);
                societyMapper.insertSocietyImage(societyImage);
            }
        }
    }

    private void processSocietyKeywords(Integer socId, List<String> socKeywords) {
        if (socKeywords != null) {
            for (String keyword : socKeywords) {
                SocietyKeyword societyKeyword = new SocietyKeyword();
                societyKeyword.setSocId(socId);
                societyKeyword.setKeyword(keyword);
                societyMapper.insertSocietyKeyword(societyKeyword);
            }
        }
    }

    // Helper method to save uploaded logo
    private String saveLogo(MultipartFile socLogo) {
        // Save the file and return the relative path
        // You need to implement the logic to store the file on the server
        // Example: fileStorageService.saveFile(socLogo);
        return "path/to/logo";
    }

    // Helper method to save uploaded images
    private String saveImage(MultipartFile socImage) {
        // Save the file and return the relative path
        // You need to implement the logic to store the file on the server
        // Example: fileStorageService.saveFile(socImage);
        return "path/to/image";
    }

    @Override
    public GetStudentProfileResponse getStudentProfile(String username) {
        User user = userMapper.getUserByUsername(username);

        if (user != null) {
            //return studentMapper.getStudentByUserId(user.getId());
        }
        return null; // Return null if the user or student is not found
    }

    @Override
    public void modifyStudentProfile(String username, ModifyStuProfileReq modifyRequest) {

    }
}
