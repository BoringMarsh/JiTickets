package cn.edu.tongji.springbackend.service;

import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.model.Activity;

import java.util.List;

public interface SocietyActivityService {
    int uploadActivity(UploadActReq uploadActReq);
    List<ActivityDetailedInfo> getActivityList(int beginNumber, int endNumber);
    List<Activity> getSocActivities(int socId, int status, int order,
                                        List<String> keywords, String query, String uploadTime,
                                        String regEndTime, int page, int pageSize);
    GetSocietyActivityListResponse getSocietyActivityList(int socId, int beginNumber, int endNumber);
}
