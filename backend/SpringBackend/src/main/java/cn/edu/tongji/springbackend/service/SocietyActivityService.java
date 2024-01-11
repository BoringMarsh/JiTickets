package cn.edu.tongji.springbackend.service;

import cn.edu.tongji.springbackend.dto.*;

import java.util.List;

public interface SocietyActivityService {
    int uploadActivity(UploadActReq uploadActReq);
    List<ActivityDetailedInfo> getActivityList(int beginNumber, int endNumber);
}
