package cn.edu.tongji.springbackend.service;

import cn.edu.tongji.springbackend.dto.GetSocietyProfileResponse;
import cn.edu.tongji.springbackend.dto.GetStudentProfileResponse;
import cn.edu.tongji.springbackend.dto.ModifySocProfileReq;
import cn.edu.tongji.springbackend.dto.ModifyStuProfileReq;

import org.springframework.stereotype.Service;

@Service
public interface ProfileService {
    GetStudentProfileResponse getStudentProfile(String username);
    void modifyStudentProfile(ModifyStuProfileReq modifyRequest);
    GetSocietyProfileResponse getSocietyProfileInfo(String username);

    void modifySocietyProfile(ModifySocProfileReq modifyRequest);
}
