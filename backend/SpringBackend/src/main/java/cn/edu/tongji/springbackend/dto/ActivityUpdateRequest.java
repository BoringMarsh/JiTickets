package cn.edu.tongji.springbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityUpdateRequest {
    private Integer actId;
    private String actName;
    private String actIntro;
    private String actLocation;
    private Double ticketPrice;
    private LocalDateTime regStartTime;
    private LocalDateTime regEndTime;
    private LocalDateTime actTime;
    private Integer actCapacity;
    private List<String> actKeywords;
    private List<String> base64ActImages;
}


