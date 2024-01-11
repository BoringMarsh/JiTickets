package cn.edu.tongji.springbackend.dto;

import cn.edu.tongji.springbackend.model.SocietyAdminRegistration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifySocProfileReq {
    private Integer userId;
    private String username;
    private String email;
    private String phone;
    private String campus;
    private String socName;
    private String socIntro;
    private String socType;
    private List<String> socKeywords;
    private List<SocietyAdminRegistration> socAdminRegs;

}