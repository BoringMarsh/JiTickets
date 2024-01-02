package cn.edu.tongji.springbackend.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Indent {
    private Integer indId;
    private Double indPrice;
    private LocalDateTime indCreateTime;
    private String indVerifyCode;
    private String indName;
    private String indStuNo;
    private String indNotes;
    private Integer indStatus;
    private Double indRating;
    private Integer actId;
    private Integer stuId;
    private LocalDateTime indRtime;
    private String indRnotes;
    private Double indRmoney;
}
