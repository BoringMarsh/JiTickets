package cn.edu.tongji.springbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetStudentProfileResponse {
    //User attributes
    private Integer id;
    private String username;
    private String email;
    private String phone;
    private String campus;
    private Integer accountStatus;
    private Double balance;
    private String regTime;
    private Integer role;

    //Student attributes
    private String stuName;
    private String stuYear;
    private String stuSchool;
    private String stuMajor;
    private String stuNotes;

    // from student_keyword
    private List<String> stuKeywords;
}