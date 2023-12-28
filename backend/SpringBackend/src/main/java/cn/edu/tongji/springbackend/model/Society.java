package cn.edu.tongji.springbackend.model;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
public class Society {
    private Integer socId;
    private String socName;
    private String socIntro;
    private String socType;
    private String socLogo;
}

