package cn.edu.tongji.springbackend.service;


import cn.edu.tongji.springbackend.dto.AddAppealRequest;
import cn.edu.tongji.springbackend.dto.AppealDetailedInfo;
import cn.edu.tongji.springbackend.dto.GetAppealPageResponse;
import cn.edu.tongji.springbackend.dto.SetUserProhibitedStatusRequest;

public interface OrderService {
    GetAppealPageResponse getAppealPage(int page);
    AppealDetailedInfo getAppeal(int appId) throws Exception;
    void addAppeal(AddAppealRequest addAppealRequest);
    void setUserProhibitedStatus(SetUserProhibitedStatusRequest setUserProhibitedStatusRequest);
}
