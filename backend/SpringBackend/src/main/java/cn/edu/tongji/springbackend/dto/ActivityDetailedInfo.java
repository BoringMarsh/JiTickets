package cn.edu.tongji.springbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDetailedInfo {
    private String actName;
    private String actIntro;
    private String actLocation;
    private Double ticketPrice;
    private LocalDateTime uploadTime;
    private LocalDateTime regStartTime;
    private LocalDateTime regEndTime;
    private LocalDateTime actTime;
    private Integer actCapability;
    private Integer actLeft;
    private Double actRating;
    private Integer ratingNum;
    private Integer socId;
    private List<String> images;
    private List<String> keywords;
}
