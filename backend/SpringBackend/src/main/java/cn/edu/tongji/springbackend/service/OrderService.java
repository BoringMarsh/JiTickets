package cn.edu.tongji.springbackend.service;


import cn.edu.tongji.springbackend.dto.AddAppealRequest;
import cn.edu.tongji.springbackend.dto.GetAppealPageResponse;
import cn.edu.tongji.springbackend.dto.SetUserProhibitedStatusRequest;
import cn.edu.tongji.springbackend.model.Appeal;

public interface OrderService {
    GetAppealPageResponse getAppealPage(int page);
    Appeal addAppeal(AddAppealRequest addAppealRequest);
    void setUserProhibitedStatus(SetUserProhibitedStatusRequest setUserProhibitedStatusRequest);
}
