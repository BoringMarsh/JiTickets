package cn.edu.tongji.springbackend.service;

import org.springframework.stereotype.Service;

@Service
public interface EncryptService {
    String encryptPassword(String password);
    boolean passwordCmp(String encrypted, String origin);
}
