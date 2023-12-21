package cn.edu.tongji.springbackend.dto;

import cn.edu.tongji.springbackend.model.Society;
import cn.edu.tongji.springbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetStudentProfileResponse {
    private Integer userId;
    private String username;
    private String email;
    private String phone;
    private String campus;
    private Integer loginStatus;
    private Integer accountStatus;
    private Double balance;
    private String payPassword;
    private LocalDateTime regTime;
    private Integer role;

    private Integer socId;
    private String socName;
    private String socIntro;
    private String socType;
    private String socLogo;

    // Additional fields from related entities if needed
    private List<SocietyAdmin> socAdmins;
    private List<SocietyImage> socImages;
    private List<SocietyKeyword> socKeywords;
}