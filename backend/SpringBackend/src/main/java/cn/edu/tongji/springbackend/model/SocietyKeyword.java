package cn.edu.tongji.springbackend.model;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
public class SocietyKeyword {
    private Integer socId;
    private String keyword;
}