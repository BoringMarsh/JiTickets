package cn.edu.tongji.springbackend.service;

import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.model.Browse;

import java.io.IOException;
import java.util.List;

public interface ActivityPersonalService {
    GetActivityPageResponse getActivityPage(int page);
    ActivityDetailedInfo getActivity(int actId) throws IOException;
    List<Browse> getBrowse(int browserId);
    void addBrowse(AddBrowseRequest addBrowseRequest);
    List<ActivityShortInfo> getFavour(int stuId);
    void addFavour(int stuId, int actId);
    void deleteFavour(int stuId, int actId);
    GetIndentPageResponse getIndentPage(GetIndentPageRequest getIndentPageRequest);
    IndentDetailedInfo getIndent(int indId);
    void addIndent(AddIndentRequest addIndentRequest);
    void cancelIndent(CancelIndentRequest cancelIndentRequest);
    void writeOffIndent(int indId);
    void changeIndentNotes(ChangeIndentNotesRequest changeIndentNotesRequest);
    void rateActivity(int indId, double indRating);
    void changeRating(int indId, double indRating);
}
