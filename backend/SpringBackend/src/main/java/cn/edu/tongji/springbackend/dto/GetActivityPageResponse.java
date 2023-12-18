package cn.edu.tongji.springbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetActivityPageResponse {
    private Integer currentPage;
    private Integer totalPage;
    private List<ActivityShortInfo> activityList;
}
