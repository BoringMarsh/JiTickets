package cn.edu.tongji.springbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadActReq {
    private Integer socID;
    private String actName;
    private String actIntro;
    private String actLocation;
    private Double ticketPrice;
    private String regStartTime;
    private String regEndTime;
    private String actTime;
    private Integer actCapacity;

    private List<String> actKeywords;
    private List<String> base64ActImages;
}