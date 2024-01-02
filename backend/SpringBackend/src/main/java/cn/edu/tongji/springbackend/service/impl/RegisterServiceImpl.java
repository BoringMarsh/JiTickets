package cn.edu.tongji.springbackend.service.impl;

import cn.edu.tongji.springbackend.config.FileStorageProperties;
import cn.edu.tongji.springbackend.controller.KeywordsController;
import cn.edu.tongji.springbackend.dto.GetStudentProfileResponse;
import cn.edu.tongji.springbackend.dto.ModifyStuProfileReq;
import cn.edu.tongji.springbackend.dto.RegSocietyRequest;
import cn.edu.tongji.springbackend.dto.RegStudentRequest;
import cn.edu.tongji.springbackend.exceptions.FileStorageException;
import cn.edu.tongji.springbackend.mapper.SocietyMapper;
import cn.edu.tongji.springbackend.mapper.StudentKeywordMapper;
import cn.edu.tongji.springbackend.mapper.StudentMapper;
import cn.edu.tongji.springbackend.mapper.UserMapper;
import cn.edu.tongji.springbackend.model.*;
import cn.edu.tongji.springbackend.service.RegisterService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RegisterServiceImpl implements RegisterService {
    private static final Logger logger = LoggerFactory.getLogger(KeywordsController.class);
    @Resource
    private UserMapper userMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private StudentKeywordMapper studentKeywordMapper;
    @Resource
    private SocietyMapper societyMapper;
    @Autowired
    private FileStorageProperties fileStorageProperties;

    @Override
    @Transactional
    public int registerStudent(RegStudentRequest registrationRequest) {

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

        return user.getId();
    }

    // Add other methods as needed
    @Transactional
    public int registerSociety(RegSocietyRequest registrationRequest) {
        try{
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
            if (registrationRequest.getSocLogoFile() != null && !registrationRequest.getSocLogoFile().isEmpty()) {
                String logoPath = saveLogo(registrationRequest.getSocLogoFile());
                society.setSocLogo(logoPath);
            }
            // Insert the society into the society table
            societyMapper.insertSociety(society);
            logger.info("Successfully inserted society with ID: {}", user.getId());
            // Process and save society admins, images, and keywords
            processSocietyAdmins(user.getId(), registrationRequest.getSocAdminRegs());
            logger.info("Successfully inserted socAdmins with ID: {}", user.getId());
            processSocietyImages(user.getId(), registrationRequest.getSocImageFiles());
            logger.info("Successfully inserted socImages with ID: {}", user.getId());
            processSocietyKeywords(user.getId(), registrationRequest.getSocKeywords());
            logger.info("Successfully inserted socKeywords with ID: {}", user.getId());

            return user.getId();
        } catch (Exception ex) {
            logger.error("Error occurred in registerSociety: " + ex.getMessage(), ex);
            throw ex; // Re-throw the exception if you want to propagate it up the call stack
        }
    }

    // Helper methods for processing society admins, images, and keywords
    private void processSocietyAdmins(Integer socId, List<SocietyAdminRegistration> socAdminRegs) {
        if (socAdminRegs != null) {
            for (SocietyAdminRegistration adminReg : socAdminRegs) {
                SocietyAdmin admin = new SocietyAdmin();
                admin.setSocId(socId);
                admin.setSocAdminNo(adminReg.getSocAdminNo());
                admin.setSocAdminName(adminReg.getSocAdminName());
                admin.setSocAdminEmail(adminReg.getSocAdminEmail());
                admin.setSocAdminPhone(adminReg.getSocAdminPhone());
                societyMapper.insertSocietyAdmin(admin);
            }
        }
    }

    private void processSocietyImages(Integer socId, List<String> socImageFiles) {
        if (socImageFiles != null) {
            for (String base64Image : socImageFiles) {
                if (base64Image != null && !base64Image.isEmpty()) {
                    String imagePath = saveImage(base64Image);
                    SocietyImage societyImage = new SocietyImage();
                    societyImage.setSocId(socId);
                    societyImage.setSocImage(imagePath);
                    societyMapper.insertSocietyImage(societyImage);
                }
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

    private String saveLogo(String base64Logo) {
        try {
            // Specify the directory where you want to store logos
            String logoUploadDir = fileStorageProperties.getLogoUploadDir();
            Files.createDirectories(Paths.get(logoUploadDir));
            // Decode the Base64 string
            byte[] imageBytes = Base64.getDecoder().decode(base64Logo.split(",")[1]);
            // Generate a unique filename to avoid collisions
            String uniqueFileName = UUID.randomUUID().toString() + ".jpg"; // Assuming JPEG format
            Path targetLocation = Paths.get(logoUploadDir, uniqueFileName);
            // Save the decoded bytes to file
            Files.write(targetLocation, imageBytes);
            // Return the relative path
            return logoUploadDir + uniqueFileName;
        } catch (IOException ex) {
            throw new FileStorageException("Failed to store logo file", ex);
        } catch (Exception ex) {
            logger.error("General Exception occurred: " + ex.getMessage(), ex);
            throw ex; // 或者处理异常
        }
    }

    private String saveImage(String base64Image) {
        try {
            // Specify the directory where you want to store images
            String imageUploadDir = fileStorageProperties.getImageUploadDir();
            Files.createDirectories(Paths.get(imageUploadDir));

            // Decode the Base64 string
            byte[] imageBytes = Base64.getDecoder().decode(base64Image.split(",")[1]);

            // Generate a unique filename to avoid collisions
            String uniqueFileName = UUID.randomUUID().toString() + ".jpg"; // Assuming JPEG format
            Path targetLocation = Paths.get(imageUploadDir, uniqueFileName);

            // Save the decoded bytes to file
            Files.write(targetLocation, imageBytes);

            // Return the relative path
            return imageUploadDir + uniqueFileName;
        } catch (IOException ex) {
            throw new FileStorageException("Failed to store image file", ex);
        }
    }



    @Override
    public GetStudentProfileResponse getStudentProfile(String username) {
        //User user = userMapper.getUserByUsername(username);

        //if (user != null) {
            //return studentMapper.getStudentByUserId(user.getId());
        //}
        return null; // Return null if the user or student is not found
    }

    @Override
    public void modifyStudentProfile(String username, ModifyStuProfileReq modifyRequest) {

    }
}