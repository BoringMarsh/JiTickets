package cn.edu.tongji.springbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndentDetailedInfo {
    private Integer indId;
    private Double indPrice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime indCreateTime;

    private String indVerifyCode;
    private String indName;
    private String indStuNo;
    private String indNotes;
    private Integer indStatus;
    private Double indRating;
    private Integer actId;
    private Integer stuId;
    private Integer socId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime indRtime;

    private String indRnotes;
    private Double indRmoney;
    private String actName;
    private String actLocation;
    private Double ticketPrice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actTime;

    private Double actRating;
}
