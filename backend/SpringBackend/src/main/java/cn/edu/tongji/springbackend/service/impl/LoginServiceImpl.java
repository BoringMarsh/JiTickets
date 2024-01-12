package cn.edu.tongji.springbackend.service.impl;


import cn.edu.tongji.springbackend.controller.KeywordsController;
import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.exceptions.LoginException;
import cn.edu.tongji.springbackend.mapper.UserMapper;
import cn.edu.tongji.springbackend.model.*;
import cn.edu.tongji.springbackend.service.EncryptService;
import cn.edu.tongji.springbackend.service.LoginService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private EncryptService encryptService;
    @Resource
    private UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(KeywordsController.class);

    @Override
    public LoginResponse login(String username, String password) {
        try {
            User user = userMapper.getUserByUsername(username);
            if (user == null) {
                // Account does not exist
                logger.warn("Login failed: Account does not exist.");
                throw new LoginException("Account does not exist");
            }

            if (!encryptService.passwordCmp(user.getPassword(), password)) {
                // Incorrect password
                logger.warn("Login failed: Incorrect password.");
                throw new LoginException("Incorrect password");
            }

            // Login successful
            return new LoginResponse("success", user.getId(),
                    user.getUsername(), user.getCampus(), user.getAccountStatus(), user.getBalance(), user.getRole());
        } catch (Exception ex) {
            logger.error("Error occurred in login: " + ex.getMessage(), ex);
            throw ex; // Re-throw the exception if you want to propagate it up the call stack
        }
    }

}
