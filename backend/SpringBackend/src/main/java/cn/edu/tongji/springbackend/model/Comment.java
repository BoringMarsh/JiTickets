package cn.edu.tongji.springbackend.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Comment {
    private Integer cmtId;
    private Integer cmtFather;
    private String cmtContent;
    private LocalDateTime cmtTime;
    private Integer actId;
    private Integer userId;
}
