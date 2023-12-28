package cn.edu.tongji.springbackend.service;

import cn.edu.tongji.springbackend.dto.*;

import java.util.List;

public interface ActivityPersonalService {
    GetActivityPageResponse getActivityPage(int page);
    ActivityDetailedInfo getActivity(int actId);
    void addBrowse(AddBrowseRequest addBrowseRequest);
    List<ActivityShortInfo> getFavour(int stuId);
    void addFavour(FavourRequest favourRequest);
    void deleteFavour(FavourRequest favourRequest);
    GetIndentPageResponse getIndentPage(GetIndentPageRequest getIndentPageRequest);
    IndentDetailedInfo getIndent(int indId);
    void addIndent(AddIndentRequest addIndentRequest);
    void cancelIndent(CancelIndentRequest cancelIndentRequest);
    void writeOffIndent(int indId);
    void changeIndentNotes(ChangeIndentNotesRequest changeIndentNotesRequest);
}
