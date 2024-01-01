package cn.edu.tongji.springbackend.dto;


import cn.edu.tongji.springbackend.model.SocietyAdminRegistration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegSocietyRequest {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String campus;
    private String payPassword;
    private String socName;
    private String socIntro;
    private String socType;
    // Add other fields as needed
    private String socLogoFile; // 接收Base64字符串
    private List<SocietyAdminRegistration> socAdminRegs;
    private List<String> socImageFiles; // 接收Base64字符串列表
    private List<String> socKeywords;
}