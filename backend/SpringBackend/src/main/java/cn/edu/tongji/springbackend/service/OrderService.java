package cn.edu.tongji.springbackend.service;


import cn.edu.tongji.springbackend.dto.AddAppealRequest;
import cn.edu.tongji.springbackend.dto.GetAppealPageResponse;

import java.io.IOException;

public interface OrderService {
    GetAppealPageResponse getAppealPage(int timeOrder, int beginNumber, int endNumber) throws IOException;
    void addAppeal(AddAppealRequest addAppealRequest);
}
