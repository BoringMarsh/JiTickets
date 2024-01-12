package cn.edu.tongji.springbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetStudentPageResponse {
    private Integer count;
    private List<StudentProfile> list;
}
