package cn.edu.tongji.springbackend.service.impl;

import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.exceptions.ActivityFullException;
import cn.edu.tongji.springbackend.mapper.*;
import cn.edu.tongji.springbackend.model.*;
import cn.edu.tongji.springbackend.service.ActivityPersonalService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityPersonalServiceImpl implements ActivityPersonalService {
    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private ActivityImageMapper activityImageMapper;
    @Resource
    private ActivityKeywordMapper activityKeywordMapper;
    @Resource
    private BrowseMapper browseMapper;
    @Resource
    private FavourMapper favourMapper;
    @Resource
    private IndentMapper indentMapper;

    @Override
    public GetActivityPageResponse getActivityPage(int page) {
        final int pageSize = 30;
        final int totalPage = (int) Math.ceil((double) activityMapper.getCount() / pageSize);

        if (totalPage == 0)
            return new GetActivityPageResponse(0, 0, new ArrayList<>());

        page = (page > totalPage) ? totalPage - 1 : page - 1;
        List<ActivityShortInfo> activityShortInfos = new ArrayList<>();

        for (Activity activity : activityMapper.getByPage(pageSize, page * pageSize)) {
            activityShortInfos.add(new ActivityShortInfo(
                    activity.getActId(),
                    activity.getActName(),
                    activity.getActLocation(),
                    activity.getUploadTime(),
                    activity.getRegStartTime(),
                    activity.getActTime()
            ));
        }

        return new GetActivityPageResponse(
                page + 1,
                totalPage,
                activityShortInfos
        );
    }

    @Override
    public ActivityDetailedInfo getActivity(int actId) {
        Activity activity = activityMapper.getByActId(actId);
        List<String> images = new ArrayList<>();
        List<String> keywords = new ArrayList<>();

        for (ActivityImage image : activityImageMapper.getById(actId)) {
            images.add(image.getActImage());
        }

        for (ActivityKeyword keyword : activityKeywordMapper.getById(actId)) {
            keywords.add(keyword.getKeyword());
        }

        return new ActivityDetailedInfo(
                activity.getActName(),
                activity.getActIntro(),
                activity.getActLocation(),
                activity.getTicketPrice(),
                activity.getUploadTime(),
                activity.getRegStartTime(),
                activity.getRegEndTime(),
                activity.getActTime(),
                activity.getActCapacity(),
                activity.getActLeft(),
                activity.getActRating(),
                activity.getRatingNum(),
                activity.getSocId(),
                images,
                keywords
        );
    }

    @Override
    public void addBrowse(AddBrowseRequest addBrowseRequest) {
        browseMapper.add(Browse.builder()
                .broTimeStart(addBrowseRequest.getBroTimeStart())
                .actId(addBrowseRequest.getActId())
                .browserId(addBrowseRequest.getBrowserId())
                .whetherBuy(addBrowseRequest.getWhetherBuy())
                .build()
        );
    }

    @Override
    public List<ActivityShortInfo> getFavour(int stuId) {
        List<ActivityShortInfo> activityShortInfos = new ArrayList<>();

        for (Favour favour : favourMapper.getById(stuId)) {
            Activity activity = activityMapper.getByActId(favour.getActId());

            activityShortInfos.add(new ActivityShortInfo(
                    activity.getActId(),
                    activity.getActName(),
                    activity.getActLocation(),
                    activity.getUploadTime(),
                    activity.getRegStartTime(),
                    activity.getActTime()
            ));
        }

        return activityShortInfos;
    }

    @Override
    public void addFavour(FavourRequest favourRequest) {
        favourMapper.add(Favour.builder()
                .actId(favourRequest.getActId())
                .stuId(favourRequest.getStuId())
                .build()
        );
    }

    @Override
    public void deleteFavour(FavourRequest favourRequest) {
        favourMapper.delete(Favour.builder()
                .actId(favourRequest.getActId())
                .stuId(favourRequest.getStuId())
                .build()
        );
    }

    @Override
    public GetIndentPageResponse getIndentPage(GetIndentPageRequest getIndentPageRequest) {
        final int pageSize = 5;
        final int totalPage = (int) Math.ceil((double) indentMapper.stuGetCount(getIndentPageRequest.getStuId()) / pageSize);

        if (totalPage == 0)
            return new GetIndentPageResponse(0, 0, new ArrayList<>());

        int page = getIndentPageRequest.getPage();
        page = (page > totalPage) ? totalPage - 1 : page - 1;
        List<IndentShortInfo> indentShortInfos = new ArrayList<>();

        for (Indent indent : indentMapper.stuGetByPage(getIndentPageRequest.getStuId(), pageSize, page * pageSize)) {
            indentShortInfos.add(new IndentShortInfo(
                    indent.getIndId(),
                    indent.getIndPrice(),
                    indent.getIndCreateTime(),
                    indent.getIndStatus(),
                    indent.getIndRating(),
                    indent.getActId(),
                    activityMapper.getByActId(indent.getActId()).getActName()
            ));
        }

        return new GetIndentPageResponse(
                page + 1,
                totalPage,
                indentShortInfos
        );
    }

    @Override
    public IndentDetailedInfo getIndent(int indId) {
        Indent indent = indentMapper.getByIndId(indId);
        Activity activity = activityMapper.getByActId(indent.getActId());

        return new IndentDetailedInfo(
                indent.getIndId(),
                indent.getIndPrice(),
                indent.getIndCreateTime(),
                indent.getIndVerifyCode(),
                indent.getIndName(),
                indent.getIndStuNo(),
                indent.getIndNotes(),
                indent.getIndStatus(),
                indent.getIndRating(),
                indent.getActId(),
                indent.getStuId(),
                indent.getSocId(),
                indent.getIndRtime(),
                indent.getIndRnotes(),
                indent.getIndRmoney(),
                activity.getActName(),
                activity.getActLocation(),
                activity.getTicketPrice(),
                activity.getActTime(),
                activity.getActRating() / activity.getRatingNum()
        );
    }

    @Override
    @Transactional
    public void addIndent(AddIndentRequest addIndentRequest) {
        Activity activity = activityMapper.getByActId(addIndentRequest.getActId());

        if (activity.getActLeft() == 0) {
            throw new ActivityFullException("activity full");
        }

        indentMapper.add(Indent.builder()
                .indPrice(addIndentRequest.getIndPrice())
                .indCreateTime(addIndentRequest.getIndCreateTime())
                .indVerifyCode(addIndentRequest.getIndVerifyCode())
                .indName(addIndentRequest.getIndName())
                .indStuNo(addIndentRequest.getIndStuNo())
                .indNotes(addIndentRequest.getIndNotes())
                .indStatus(0)
                .actId(addIndentRequest.getActId())
                .stuId(addIndentRequest.getStuId())
                .socId(addIndentRequest.getSocId())
                .build()
        );

        activityMapper.update(Activity.builder()
                .actId(activity.getActId())
                .actLeft(activity.getActLeft() - 1)
                .build()
        );
    }

    @Override
    @Transactional
    public void cancelIndent(CancelIndentRequest cancelIndentRequest) {
        final int actId = indentMapper.getActIdByIndId(cancelIndentRequest.getIndId());
        Activity activity = activityMapper.getByActId(actId);

        indentMapper.update(Indent.builder()
                .indId(cancelIndentRequest.getIndId())
                .indStatus(2)
                .indRtime(cancelIndentRequest.getIndRtime())
                .indRnotes(cancelIndentRequest.getIndRnotes())
                .indRmoney(cancelIndentRequest.getIndRmoney())
                .build()
        );

        activityMapper.update(Activity.builder()
                .actId(activity.getActId())
                .actLeft(activity.getActLeft() + 1)
                .build()
        );
    }

    @Override
    public void writeOffIndent(int indId) {
        indentMapper.update(Indent.builder()
                .indId(indId)
                .indStatus(1)
                .build()
        );
    }

    @Override
    public void changeIndentNotes(ChangeIndentNotesRequest changeIndentNotesRequest) {
        indentMapper.update(Indent.builder()
                .indId(changeIndentNotesRequest.getIndId())
                .indNotes(changeIndentNotesRequest.getNotes())
                .build()
        );
    }
}
