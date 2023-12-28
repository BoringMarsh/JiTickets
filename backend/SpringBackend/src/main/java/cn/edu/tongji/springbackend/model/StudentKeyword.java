package cn.edu.tongji.springbackend.model;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
public class StudentKeyword {
    private Integer stuId;
    private String keyword;
    private Double preferWeight;
}