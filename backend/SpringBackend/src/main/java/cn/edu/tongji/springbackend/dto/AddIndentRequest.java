package cn.edu.tongji.springbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddIndentRequest {
    private Double indPrice;
    private LocalDateTime indCreateTime;
    private String indVerifyCode;
    private String indName;
    private String indStuNo;
    private String indNotes;
    private Integer stuId;
    private Integer socId;
}
