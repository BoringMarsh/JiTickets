package cn.edu.tongji.springbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddAppealRequest {
    private Integer appMatters;
    private String appContent;
    private Integer userId;
    private Integer actId;
    private Integer cmtId;
    private Integer complainantId;
}
