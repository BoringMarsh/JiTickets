package cn.edu.tongji.springbackend.service;

import cn.edu.tongji.springbackend.dto.RegSocietyRequest;
import cn.edu.tongji.springbackend.dto.RegStudentRequest;
import cn.edu.tongji.springbackend.mapper.StudentKeywordMapper;
import cn.edu.tongji.springbackend.mapper.StudentMapper;
import cn.edu.tongji.springbackend.model.Student;
import cn.edu.tongji.springbackend.model.StudentKeyword;
import cn.edu.tongji.springbackend.model.User;
import cn.edu.tongji.springbackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public interface UserService {
    void registerStudent(RegStudentRequest registrationRequest);
    void registerSociety(RegSocietyRequest registrationRequest);
}
