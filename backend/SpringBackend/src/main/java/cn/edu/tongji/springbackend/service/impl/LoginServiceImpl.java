package cn.edu.tongji.springbackend.service.impl;

import cn.edu.tongji.springbackend.config.FileStorageProperties;
import cn.edu.tongji.springbackend.controller.KeywordsController;
import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.exceptions.LoginException;
import cn.edu.tongji.springbackend.mapper.SocietyMapper;
import cn.edu.tongji.springbackend.mapper.StudentKeywordMapper;
import cn.edu.tongji.springbackend.mapper.StudentMapper;
import cn.edu.tongji.springbackend.mapper.UserMapper;
import cn.edu.tongji.springbackend.model.*;
import cn.edu.tongji.springbackend.service.LoginService;
import cn.edu.tongji.springbackend.service.RegisterService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(KeywordsController.class);

    @Override
    public LoginResponse login(String username, String password) {
        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            // Account does not exist
            logger.warn("Login failed: Account does not exist.");
            throw new LoginException("Account does not exist");
        }

        if (!password.equals(user.getPassword())) {
            // Incorrect password
            logger.warn("Login failed: Incorrect password.");
            throw new LoginException("Incorrect password");
        }

        // Login successful
        return new LoginResponse("success", user.getId(),
                user.getUsername(), user.getCampus(), user.getAccountStatus(), user.getBalance(), user.getRole());
    }
}
