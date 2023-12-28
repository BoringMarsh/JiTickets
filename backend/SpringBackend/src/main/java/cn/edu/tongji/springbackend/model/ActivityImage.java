package cn.edu.tongji.springbackend.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivityImage {
    private Integer actId;
    private String actImage;
}
