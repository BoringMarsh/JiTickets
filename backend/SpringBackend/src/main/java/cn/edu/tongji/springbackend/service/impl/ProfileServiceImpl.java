package cn.edu.tongji.springbackend.service.impl;


import cn.edu.tongji.springbackend.controller.KeywordsController;
import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.exceptions.LoginException;
import cn.edu.tongji.springbackend.exceptions.NotFoundException;
import cn.edu.tongji.springbackend.mapper.*;
import cn.edu.tongji.springbackend.model.*;
import cn.edu.tongji.springbackend.service.ProfileService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
public class ProfileServiceImpl implements ProfileService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private StudentKeywordMapper studentKeywordMapper;
    @Resource
    private SocietyMapper societyMapper;
    @Resource
    private SocietyKeywordMapper societyKeywordMapper;
    @Resource
    private SocietyAdminMapper societyAdminMapper;

    private static final Logger logger = LoggerFactory.getLogger(KeywordsController.class);

    @Override
    public GetStudentProfileResponse getStudentProfile(String username) {

        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        int userId = user.getId();
        // Formatting LocalDateTime to String
        LocalDateTime localDateTime = user.getRegTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);

        Student student = studentMapper.getStudentById(userId);
        if (student == null) {
            throw new NotFoundException("Student not found");
        }
        List<String> studentKeywords = studentKeywordMapper.getStudentKeywords(userId);

        // Create and return the response object
        return new GetStudentProfileResponse(
                user.getId(), user.getUsername(), user.getEmail(), user.getPhone(), user.getCampus(),
                user.getAccountStatus(), user.getBalance(), formattedDateTime, user.getRole(),
                student.getStuName(), student.getStuYear(), student.getStuSchool(), student.getStuMajor(),
                student.getStuNotes(), studentKeywords
        );
    }

    @Override
    public void modifyStudentProfile(ModifyStuProfileReq modifyRequest) {
        int userId = modifyRequest.getUserId();
        User user = userMapper.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        Student student = studentMapper.getStudentById(userId);
        if (student == null) {
            throw new NotFoundException("Student not found");
        }

        // Update student attributes based on the modifyRequest
        user.setEmail(modifyRequest.getEmail());
        user.setPhone(modifyRequest.getPhone());
        user.setCampus(modifyRequest.getCampus());
        student.setStuName(modifyRequest.getStuName());
        student.setStuYear(modifyRequest.getStuYear());
        student.setStuSchool(modifyRequest.getStuSchool());
        student.setStuMajor(modifyRequest.getStuMajor());
        student.setStuNotes(modifyRequest.getStuNotes());
        logger.info("start to update keywords");
        // Update keywords
        List<String> newKeywords = modifyRequest.getStuKeywords();
        // Delete existing keywords for the student
        studentKeywordMapper.deleteKeywordsByStudentId(userId);
        // Insert new keywords
        if (newKeywords != null && !newKeywords.isEmpty()) {
            for (String keyword : newKeywords) {
                StudentKeyword studentKeyword = new StudentKeyword();
                studentKeyword.setStuId(userId);
                studentKeyword.setKeyword(keyword);
                studentKeywordMapper.insertStudentKeyword(studentKeyword);
            }
        }
        logger.info("done update keywords");
        // Update the student profile in the database
        studentMapper.updateStudent(student);
        userMapper.updateUser(user);
    }

    @Override
    public GetSocietyProfileResponse getSocietyProfileInfo(String username) {
        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        int userId = user.getId();
        // Formatting LocalDateTime to String
        LocalDateTime localDateTime = user.getRegTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);

        Society society = societyMapper.getSocietyById(userId);
        if (society == null) {
            throw new NotFoundException("Society not found");
        }
        List<String> societyKeywords = societyKeywordMapper.getSocietyKeywords(userId);
        List<SocietyAdmin> societyAdmins = societyAdminMapper.getSocietyAdmins(userId);

        // 读取logo文件并转换为Base64字符串
        String logoBase64 = readAndConvertToBase64(society.getSocLogo());
        logoBase64 = "data:image/jpg;base64," + logoBase64;

        // 查询并读取社会的所有image文件并转换为Base64字符串列表
        List<String> imageBase64List = new ArrayList<>();
        List<SocietyImage> societyImages = societyMapper.getSocietyImagesBySocietyId(userId);
        for (SocietyImage image : societyImages) {
            String imageBase64 = readAndConvertToBase64(image.getSocImage());
            imageBase64 = "data:image/jpg;base64," + imageBase64;
            imageBase64List.add(imageBase64);
        }
        // Create and return the response object
        return new GetSocietyProfileResponse(
                user.getId(), user.getUsername(), user.getEmail(), user.getPhone(), user.getCampus(),
                user.getAccountStatus(), user.getBalance(), formattedDateTime, user.getRole(),
                society.getSocName(), society.getSocIntro(), society.getSocType(),
                societyKeywords, societyAdmins, logoBase64, imageBase64List
        );
    }

    // 读取文件并将内容转换为Base64字符串的方法
    private String readAndConvertToBase64(String filePath) {
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
            return Base64.getEncoder().encodeToString(fileBytes);
        } catch (IOException e) {
            // 处理文件读取异常
            logger.error("Error reading and converting file to Base64: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void modifySocietyProfile(ModifySocProfileReq modifyRequest) {
        int userId = modifyRequest.getUserId();
        User user = userMapper.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        Society society = societyMapper.getSocietyById(userId);
        if (society == null) {
            throw new NotFoundException("Society not found");
        }

        // Update student attributes based on the modifyRequest
        user.setEmail(modifyRequest.getEmail());
        user.setPhone(modifyRequest.getPhone());
        user.setCampus(modifyRequest.getCampus());
        society.setSocName(modifyRequest.getSocName());
        society.setSocIntro(modifyRequest.getSocIntro());
        society.setSocType(modifyRequest.getSocType());
        logger.info("start to update keywords");
        processSocietyKeywords(userId, modifyRequest.getSocKeywords());
        logger.info("done update keywords");
        logger.info("start to update admins");
        processSocietyAdmins(userId, modifyRequest.getSocAdminRegs());
        logger.info("done update admins");
        societyMapper.updateSociety(society);
        userMapper.updateUser(user);
        logger.info("done update user");
    }
    private void processSocietyKeywords(Integer socId, List<String> socKeywords) {
        // Delete existing keywords for the society
        societyKeywordMapper.deleteKeywordsBySocietyId(socId);
        if (socKeywords != null) {
            for (String keyword : socKeywords) {
                SocietyKeyword societyKeyword = new SocietyKeyword();
                societyKeyword.setSocId(socId);
                societyKeyword.setKeyword(keyword);
                societyMapper.insertSocietyKeyword(societyKeyword);
            }
        }
    }
    // Helper methods for processing society admins, images, and keywords
    private void processSocietyAdmins(Integer socId, List<SocietyAdminRegistration> socAdminRegs) {
        // Delete existing admins for the society
        societyAdminMapper.deleteAdminsBySocietyId(socId);
        // Insert new admins
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

    @Override
    public List<GetStudentProfileResponse> getStudentProfileList(int beginNumber, int endNumber) {
        try {
            // 根据起始和结束索引分页查询学生列表
            int startRow = beginNumber - 1;  // 起始行索引，减1以匹配数据库行数从0开始的情况
            int pageSize = endNumber - beginNumber + 1;  // 检索的数据行数
            List<Student> studentList = studentMapper.getStudentListByRange(beginNumber, endNumber);
            // 处理数据库记录不足的情况
            if (studentList.isEmpty()) {
                return new ArrayList<>(); // 返回一个空列表
            }
            // 创建一个存储学生资料响应的列表
            List<GetStudentProfileResponse> studentProfiles = new ArrayList<>();
            // 遍历学生列表并获取个人资料
            for (Student student : studentList) {
                Integer stuId = student.getStuId();
                // 创建学生资料响应对象
                GetStudentProfileResponse studentProfile = new GetStudentProfileResponse();
                // 设置用户属性
                User user = userMapper.getUserById(stuId);
                studentProfile.setId(user.getId());
                studentProfile.setUsername(user.getUsername());
                studentProfile.setEmail(user.getEmail());
                studentProfile.setPhone(user.getPhone());
                studentProfile.setCampus(user.getCampus());
                studentProfile.setAccountStatus(user.getAccountStatus());
                studentProfile.setBalance(user.getBalance());
                // 格式化注册时间
                LocalDateTime regTime = user.getRegTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                studentProfile.setRegTime(regTime.format(formatter));
                studentProfile.setRole(user.getRole());

                // 设置学生属性
                studentProfile.setStuName(student.getStuName());
                studentProfile.setStuYear(student.getStuYear());
                studentProfile.setStuSchool(student.getStuSchool());
                studentProfile.setStuMajor(student.getStuMajor());
                studentProfile.setStuNotes(student.getStuNotes());

                // 获取学生的关键字信息并设置到响应对象中
                List<String> studentKeywords = studentKeywordMapper.getStudentKeywords(stuId);
                studentProfile.setStuKeywords(studentKeywords);

                // 将学生资料添加到响应列表
                studentProfiles.add(studentProfile);
            }
            return studentProfiles;
        } catch (Exception e) {
            // 处理异常情况并返回适当的响应
            logger.error("Failed to retrieve student profiles: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve student profiles: " + e.getMessage());
        }
    }

    @Override
    public List<GetSocietyProfileResponse> getSocietyProfileList(int beginNumber, int endNumber) {
        try {
            // 根据起始和结束索引分页查询社团列表
            int startRow = beginNumber - 1;  // 起始行索引，减1以匹配数据库行数从0开始的情况
            int pageSize = endNumber - beginNumber + 1;  // 检索的数据行数
            List<Society> societyList = societyMapper.getSocietyListByRange(beginNumber, endNumber);
            // 处理数据库记录不足的情况
            if (societyList.isEmpty()) {
                return new ArrayList<>(); // 返回一个空列表
            }
            // 创建一个存储学生资料响应的列表
            List<GetSocietyProfileResponse> societyProfiles = new ArrayList<>();
            // 遍历学生列表并获取个人资料
            for (Society society : societyList) {
                Integer socId = society.getSocId();
                // 创建社团资料响应对象
                GetSocietyProfileResponse societyProfile = new GetSocietyProfileResponse();
                // 设置用户属性
                User user = userMapper.getUserById(socId);
                societyProfile.setUserId(user.getId());
                societyProfile.setUsername(user.getUsername());
                societyProfile.setEmail(user.getEmail());
                societyProfile.setPhone(user.getPhone());
                societyProfile.setCampus(user.getCampus());
                societyProfile.setAccountStatus(user.getAccountStatus());
                societyProfile.setBalance(user.getBalance());
                // 格式化注册时间
                LocalDateTime regTime = user.getRegTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                societyProfile.setRegTime(regTime.format(formatter));
                societyProfile.setRole(user.getRole());

                // 设置社团属性
                societyProfile.setSocName(society.getSocName());
                societyProfile.setSocIntro(society.getSocIntro());
                societyProfile.setSocType(society.getSocType());

                // 获取学生的关键字信息并设置到响应对象中
                List<String> societyKeywords = societyKeywordMapper.getSocietyKeywords(socId);
                societyProfile.setSocKeywords(societyKeywords);
                List<SocietyAdmin> societyAdmins = societyAdminMapper.getSocietyAdmins(socId);
                societyProfile.setSocAdmins(societyAdmins);

                // 读取logo文件并转换为Base64字符串
                String logoBase64 = readAndConvertToBase64(society.getSocLogo());
                logoBase64 = "data:image/jpg;base64," + logoBase64;
                societyProfile.setSocLogoFile(logoBase64);

                // 查询并读取社会的所有image文件并转换为Base64字符串列表
                List<String> imageBase64List = new ArrayList<>();
                List<SocietyImage> societyImages = societyMapper.getSocietyImagesBySocietyId(socId);
                for (SocietyImage image : societyImages) {
                    String imageBase64 = readAndConvertToBase64(image.getSocImage());
                    imageBase64 = "data:image/jpg;base64," + imageBase64;
                    imageBase64List.add(imageBase64);
                }
                societyProfile.setSocImageFiles(imageBase64List);

                // 将学生资料添加到响应列表
                societyProfiles.add(societyProfile);
            }
            return societyProfiles;
        } catch (Exception e) {
            // 处理异常情况并返回适当的响应
            logger.error("Failed to retrieve student profiles: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve student profiles: " + e.getMessage());
        }
    }

}

