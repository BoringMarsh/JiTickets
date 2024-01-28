package cn.edu.tongji.springbackend.service;

import cn.edu.tongji.springbackend.dto.*;

import org.springframework.stereotype.Service;

@Service
public interface ProfileService {
    StudentProfile getStudentProfile(String username);
    void modifyStudentProfile(ModifyStuProfileReq modifyRequest);
    SocietyProfile getSocietyProfileInfo(String username);
    void modifySocietyProfile(ModifySocProfileReq modifyRequest);
    GetStudentPageResponse getStudentProfileList(int beginNumber, int endNumber);
    GetSocietyPageResponse getSocietyProfileList(int beginNumber, int endNumber);
    void setUserProhibitedStatus(int userId, boolean ifProhibited);
    void passRegRequest(int userId);
    void refuseRegRequest(int userId);

    void updateUserBalance(String username, Double newBalance);
}
