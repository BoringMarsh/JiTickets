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
public class GetStudentProfileResponse {
    private User user;
    private Student student;
    private List<String> studentKeyword;
}