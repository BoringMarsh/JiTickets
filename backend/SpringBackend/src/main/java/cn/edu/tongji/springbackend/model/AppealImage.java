package cn.edu.tongji.springbackend.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppealImage {
    private Integer appId;
    private String appImage;
}
