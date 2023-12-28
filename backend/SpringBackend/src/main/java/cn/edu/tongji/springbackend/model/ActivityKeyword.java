package cn.edu.tongji.springbackend.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivityKeyword {
    private Integer actId;
    private String keyword;
}
