package cn.edu.tongji.springbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppealShortInfo {
    private Integer appId;
    private Integer appMatters;
    private String appContent;
    private Integer complainantId;
}
