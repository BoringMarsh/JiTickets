package cn.edu.tongji.springbackend.dto;

import cn.edu.tongji.springbackend.model.SocietyAdmin;
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
    private MultipartFile socLogoFile; // Assuming you are using Spring's MultipartFile for file upload
    private List<SocietyAdmin> socAdmins;
    private List<MultipartFile> socImageFiles; // Assuming multiple images can be uploaded
    private List<String> socKeywords;
}