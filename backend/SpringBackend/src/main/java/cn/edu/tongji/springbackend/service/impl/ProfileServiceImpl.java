package cn.edu.tongji.springbackend.service.impl;


import cn.edu.tongji.springbackend.controller.KeywordsController;
import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.exceptions.LoginException;
import cn.edu.tongji.springbackend.exceptions.NotFoundException;
import cn.edu.tongji.springbackend.mapper.StudentKeywordMapper;
import cn.edu.tongji.springbackend.mapper.StudentMapper;
import cn.edu.tongji.springbackend.mapper.UserMapper;
import cn.edu.tongji.springbackend.model.*;
import cn.edu.tongji.springbackend.service.ProfileService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class ProfileServiceImpl implements ProfileService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private StudentKeywordMapper studentKeywordMapper;

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
        GetStudentProfileResponse res = new GetStudentProfileResponse(
                user.getId(), user.getUsername(), user.getEmail(), user.getPhone(), user.getCampus(),
                user.getAccountStatus(), user.getBalance(), formattedDateTime, user.getRole(),
                student.getStuName(), student.getStuYear(), student.getStuSchool(), student.getStuMajor(),
                student.getStuNotes(), studentKeywords
        );

        return res;
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

        // Update the student profile in the database
        studentMapper.updateStudent(student);
    }
}