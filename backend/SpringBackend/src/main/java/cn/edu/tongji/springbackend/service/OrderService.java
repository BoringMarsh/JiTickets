package cn.edu.tongji.springbackend.service;


import cn.edu.tongji.springbackend.dto.AddAppealRequest;
import cn.edu.tongji.springbackend.dto.AppealDetailedInfo;
import cn.edu.tongji.springbackend.dto.GetAppealPageResponse;
import cn.edu.tongji.springbackend.dto.SetUserProhibitedStatusRequest;

import java.io.IOException;
import java.util.List;

public interface OrderService {
    GetAppealPageResponse getAppealPage(int timeOrder, int beginNum, int endNum) throws IOException;
    AppealDetailedInfo getAppeal(int appId) throws Exception;
    void addAppeal(AddAppealRequest addAppealRequest);
    void setUserProhibitedStatus(SetUserProhibitedStatusRequest setUserProhibitedStatusRequest);
}
