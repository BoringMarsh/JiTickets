package cn.edu.tongji.springbackend.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Activity {
    private Integer actId;
    private String actName;
    private String actIntro;
    private String actLocation;
    private Double ticketPrice;
    private LocalDateTime uploadTime;
    private LocalDateTime regStartTime;
    private LocalDateTime regEndTime;
    private LocalDateTime actTime;
    private Integer actCapacity;
    private Integer actLeft;
    private Double actRating;
    private Integer ratingNum;
    private Integer socId;
}

