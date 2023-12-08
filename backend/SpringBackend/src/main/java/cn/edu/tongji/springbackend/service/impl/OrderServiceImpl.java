package cn.edu.tongji.springbackend.service.impl;

import cn.edu.tongji.springbackend.dto.AddAppealRequest;
import cn.edu.tongji.springbackend.dto.GetAppealPageResponse;
import cn.edu.tongji.springbackend.dto.SetUserProhibitedStatusRequest;
import cn.edu.tongji.springbackend.mapper.AppealMapper;
import cn.edu.tongji.springbackend.mapper.UserMapper;
import cn.edu.tongji.springbackend.model.Appeal;
import cn.edu.tongji.springbackend.model.User;
import cn.edu.tongji.springbackend.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private AppealMapper appealMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public GetAppealPageResponse getAppealPage(int page) {
        final int pageSize = 30;
        final int totalPage = (int) Math.ceil((double) appealMapper.getCount() / pageSize);
        page = (page > totalPage) ? totalPage - 1 : page - 1;

        return new GetAppealPageResponse(
                page,
                totalPage,
                appealMapper.getByPage(page, pageSize)
        );
    }

    @Override
    public Appeal addAppeal(AddAppealRequest addAppealRequest) {
        Appeal appeal = Appeal.builder()
                .appMatters(addAppealRequest.getAppMatters())
                .appContent(addAppealRequest.getAppContent())
                .userId(addAppealRequest.getUserId())
                .actId(addAppealRequest.getActId())
                .cmtId(addAppealRequest.getCmtId())
                .complainantId(addAppealRequest.getComplainantId())
                .build();

        appealMapper.add(appeal);
        return appealMapper.getById(appeal.getAppId());
    }

    @Override
    public void setUserProhibitedStatus(SetUserProhibitedStatusRequest setUserProhibitedStatusRequest) {
        User user = userMapper.selectById(setUserProhibitedStatusRequest.getUserId());
        user.setAccountStatus(setUserProhibitedStatusRequest.getIfProhibited() ? 0 : 1);
        userMapper.update(user);
    }
}
