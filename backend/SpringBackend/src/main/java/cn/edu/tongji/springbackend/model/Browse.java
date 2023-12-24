package cn.edu.tongji.springbackend.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Browse {
    private LocalDateTime broTimeStart;
    private Integer actId;
    private Integer browserId;
    private Boolean whetherBuy;
}
