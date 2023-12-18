package cn.edu.tongji.springbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelIndentRequest {
    private Integer indId;
    private LocalDateTime indRtime;
    private String indRnotes;
    private Double indRmoney;
}
