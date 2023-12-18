package cn.edu.tongji.springbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppealDetailedInfo {
    private Integer appId;
    private Integer appMatters;
    private String appContent;
    private Integer userId;
    private Integer actId;
    private Integer cmtId;
    private Integer complainantId;
    private List<String> images;
}
