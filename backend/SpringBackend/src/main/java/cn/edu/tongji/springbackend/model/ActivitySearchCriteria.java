package cn.edu.tongji.springbackend.model;

import lombok.Data;

import java.util.List;

@Data
public class ActivitySearchCriteria {
    private Integer socId;
    private Integer status;
    private Integer order;
    private List<String> keyword;
    private String query;
    private String uploadTime;
    private String regEndTime;
    private Integer page;
    private Integer pageSize;
}
