package cn.edu.tongji.springbackend.service;


import cn.edu.tongji.springbackend.dto.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    LoginResponse login(String username, String password);
}