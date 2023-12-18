package cn.edu.tongji.springbackend.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Favour {
    private Integer actId;
    private Integer stuId;
}
