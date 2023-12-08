package cn.edu.tongji.springbackend.dto;

import cn.edu.tongji.springbackend.model.Appeal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAppealPageResponse {
    private Integer currentPage;
    private Integer totalPage;
    private List<Appeal> appealList;
}
