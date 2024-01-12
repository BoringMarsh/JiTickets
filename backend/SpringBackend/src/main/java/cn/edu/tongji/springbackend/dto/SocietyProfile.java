package cn.edu.tongji.springbackend.dto;

import cn.edu.tongji.springbackend.model.SocietyAdmin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocietyProfile {
    //User attributes
    private Integer userId;
    private String username;
    private String email;
    private String phone;
    private String campus;
    private Integer accountStatus;
    private Double balance;
    private String regTime;
    private Integer role;

    //Society attributes
    private String socName;
    private String socIntro;
    private String socType;

    private List<String> socKeywords;
    private List<SocietyAdmin> socAdmins;

    private String socLogoFile; // 接收Base64字符串
    private List<String> socImageFiles; // 接收Base64字符串列表

}