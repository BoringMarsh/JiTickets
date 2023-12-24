package cn.edu.tongji.springbackend.service.impl;

import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.mapper.AppealImageMapper;
import cn.edu.tongji.springbackend.mapper.AppealMapper;
import cn.edu.tongji.springbackend.mapper.UserMapper;
import cn.edu.tongji.springbackend.model.Appeal;
import cn.edu.tongji.springbackend.model.AppealImage;
import cn.edu.tongji.springbackend.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private AppealMapper appealMapper;
    @Resource
    private AppealImageMapper appealImageMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public GetAppealPageResponse getAppealPage(int page) {
        final int pageSize = 30;
        final int totalPage = (int) Math.ceil((double) appealMapper.getCount() / pageSize);

        if (totalPage == 0)
            return new GetAppealPageResponse(0, 0, new ArrayList<>());

        page = (page > totalPage) ? totalPage - 1 : page - 1;
        List<AppealShortInfo> appealShortInfos = new ArrayList<>();

        for (Appeal appeal : appealMapper.getByPage(pageSize, page * pageSize)) {
            appealShortInfos.add(new AppealShortInfo(
                    appeal.getAppId(),
                    appeal.getAppTime(),
                    appeal.getAppMatters(),
                    appeal.getAppContent(),
                    appeal.getComplainantId()
            ));
        }

        return new GetAppealPageResponse(
                page + 1,
                totalPage,
                appealShortInfos
        );
    }

    @Override
    public AppealDetailedInfo getAppeal(int appId) {
        Appeal appeal = appealMapper.getById(appId);
        List<String> images = new ArrayList<>();

        for (AppealImage image : appealImageMapper.getById(appId)) {
            images.add(image.getAppImage());
        }

        return new AppealDetailedInfo(
                appeal.getAppId(),
                appeal.getAppTime(),
                appeal.getAppMatters(),
                appeal.getAppContent(),
                appeal.getUserId(),
                appeal.getActId(),
                appeal.getCmtId(),
                appeal.getComplainantId(),
                images
        );
    }

    @Override
    public void addAppeal(AddAppealRequest addAppealRequest) {
        appealMapper.add(Appeal.builder()
                .appTime(addAppealRequest.getAppTime())
                .appMatters(addAppealRequest.getAppMatters())
                .appContent(addAppealRequest.getAppContent())
                .userId(addAppealRequest.getUserId())
                .actId(addAppealRequest.getActId())
                .cmtId(addAppealRequest.getCmtId())
                .complainantId(addAppealRequest.getComplainantId())
                .build()
        );
    }

    @Override
    public void setUserProhibitedStatus(SetUserProhibitedStatusRequest setUserProhibitedStatusRequest) {
        //User user = userMapper.selectById(setUserProhibitedStatusRequest.getUserId());
        //user.setAccountStatus(setUserProhibitedStatusRequest.getIfProhibited() ? 0 : 1);
        //userMapper.update(user);
    }
}
