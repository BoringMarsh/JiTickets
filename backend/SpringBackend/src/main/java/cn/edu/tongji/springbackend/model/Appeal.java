package cn.edu.tongji.springbackend.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Appeal {
    private Integer appId;
    private Integer appMatters;
    private String appContent;
    private Integer userId;
    private Integer actId;
    private Integer cmtId;
    private Integer complainantId;
}
