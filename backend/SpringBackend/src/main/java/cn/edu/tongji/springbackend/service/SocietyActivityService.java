package cn.edu.tongji.springbackend.service;

import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.model.Activity;
import cn.edu.tongji.springbackend.model.ActivitySearchCriteria;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface SocietyActivityService {
    int uploadActivity(UploadActReq uploadActReq);
    List<ActivityDetailedInfo> getActivityList(int beginNumber, int endNumber);

    List<SocActivityResponse> getSocActivities(@RequestBody ActivitySearchCriteria criteria);
    void updateActivity(ActivityUpdateRequest request);
}
