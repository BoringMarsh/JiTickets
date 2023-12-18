package cn.edu.tongji.springbackend.service.impl;

import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.mapper.*;
import cn.edu.tongji.springbackend.model.*;
import cn.edu.tongji.springbackend.service.ActivityPersonalService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
    public List<ActivityShortInfo> getActivityPage(int page) {
        final int pageSize = 30;
        final int totalPage = (int) Math.ceil((double) activityMapper.getCount() / pageSize);
        page = (page > totalPage) ? totalPage - 1 : page - 1;
        List<ActivityShortInfo> activityShortInfos = new ArrayList<>();

        for (Activity activity : activityMapper.getByPage(page, pageSize)) {
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
    public ActivityDetailedInfo getActivity(int id) {
        Activity activity = activityMapper.getById(id);
        List<String> images = new ArrayList<>();
        List<String> keywords = new ArrayList<>();

        for (ActivityImage image : activityImageMapper.getById(id)) {
            images.add(image.getActImage());
        }

        for (ActivityKeyword keyword : activityKeywordMapper.getById(id)) {
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
            Activity activity = activityMapper.getById(favour.getActId());

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
    public void addIndent(AddIndentRequest addIndentRequest) {
        indentMapper.add(Indent.builder()
                .indPrice(addIndentRequest.getIndPrice())
                .indCreateTime(addIndentRequest.getIndCreateTime())
                .indName(addIndentRequest.getIndName())
                .indStuNo(addIndentRequest.getIndStuNo())
                .indNotes(addIndentRequest.getIndNotes())
                .stuId(addIndentRequest.getStuId())
                .socId(addIndentRequest.getSocId())
                .build()
        );
    }

    @Override
    public void cancelIndent(CancelIndentRequest cancelIndentRequest) {
        indentMapper.update(Indent.builder()
                .indId(cancelIndentRequest.getIndId())
                .indStatus(2)
                .indRtime(cancelIndentRequest.getIndRtime())
                .indRnotes(cancelIndentRequest.getIndRnotes())
                .indRmoney(cancelIndentRequest.getIndRmoney())
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

    @Override
    public void changeIndentRating(ChangeIndentRatingRequest changeIndentRatingRequest) {
        indentMapper.update(Indent.builder()
                .indId(changeIndentRatingRequest.getIndId())
                .indRating(changeIndentRatingRequest.getRating())
                .build()
        );
    }
}
