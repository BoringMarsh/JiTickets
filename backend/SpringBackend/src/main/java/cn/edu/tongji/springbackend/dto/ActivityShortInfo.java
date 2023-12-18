package cn.edu.tongji.springbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityShortInfo {
    private Integer actId;
    private String actName;
    private String actLocation;
    private LocalDateTime uploadTime;
    private LocalDateTime regStartTime;
    private LocalDateTime actTime;
}
