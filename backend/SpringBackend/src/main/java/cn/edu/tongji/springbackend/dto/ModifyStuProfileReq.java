package cn.edu.tongji.springbackend.dto;

import cn.edu.tongji.springbackend.model.Keywords;
import cn.edu.tongji.springbackend.model.Student;
import cn.edu.tongji.springbackend.model.StudentKeyword;
import cn.edu.tongji.springbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyStuProfileReq {
    private Integer userId;
    private String email;
    private String phone;
    private String campus;
    private String stuName;
    private String stuYear;
    private String stuSchool;
    private String stuMajor;
    private String stuNotes;
    private List<String> stuKeywords;
}
