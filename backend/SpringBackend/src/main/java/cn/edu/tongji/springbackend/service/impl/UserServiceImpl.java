package cn.edu.tongji.springbackend.service.impl;


import cn.edu.tongji.springbackend.dto.RegSocietyRequest;
import cn.edu.tongji.springbackend.dto.RegStudentRequest;
import cn.edu.tongji.springbackend.mapper.SocietyMapper;
import cn.edu.tongji.springbackend.mapper.StudentKeywordMapper;
import cn.edu.tongji.springbackend.mapper.StudentMapper;
import cn.edu.tongji.springbackend.mapper.UserMapper;
import cn.edu.tongji.springbackend.model.*;
import cn.edu.tongji.springbackend.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Override
    @Transactional
    public void registerStudent(RegStudentRequest registrationRequest) {
        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(registrationRequest.getPassword());
        user.setEmail(registrationRequest.getEmail());
        user.setPhone(registrationRequest.getPhone());
        user.setCampus(registrationRequest.getCampus());
        user.setPayPassword(registrationRequest.getPayPassword());
        user.setRole(0);  // Assuming 0 is the role for normal users


        // Insert the user into the user table
        userMapper.insertUser(user);

        Student student = new Student();
        student.setStuId(user.getId());
        student.setStuName(registrationRequest.getStuName());
        student.setStuYear(registrationRequest.getStuYear());
        student.setStuSchool(registrationRequest.getStuSchool());
        student.setStuMajor(registrationRequest.getStuMajor());
        student.setStuNotes(registrationRequest.getStuNotes());

        // Insert the student into the student table
        studentMapper.insertStudent(student);

        List<StudentKeyword> studentKeywords = registrationRequest.getKeywords().stream()
                .map(keyword -> {
                    StudentKeyword studentKeyword = new StudentKeyword();
                    studentKeyword.setStuId(user.getId());
                    studentKeyword.setKeyword(keyword);
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
        user.setPayPassword(registrationRequest.getPayPassword());
        user.setRole(1);

        // Insert the user into the user table
        userMapper.insertUser(user);

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
}
