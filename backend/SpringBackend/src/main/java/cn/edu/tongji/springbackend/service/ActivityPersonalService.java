package cn.edu.tongji.springbackend.service;

import cn.edu.tongji.springbackend.dto.*;

import java.io.IOException;
import java.util.List;

public interface ActivityPersonalService {
    GetActivityPageResponse getActivityPage(int page);
    ActivityDetailedInfo getActivity(int actId) throws IOException;
    void addBrowse(AddBrowseRequest addBrowseRequest);
    List<ActivityShortInfo> getFavour(int stuId);
    void addFavour(FavourRequest favourRequest);
    void deleteFavour(int stuId, int actId);
    GetIndentPageResponse getIndentPage(GetIndentPageRequest getIndentPageRequest);
    IndentDetailedInfo getIndent(int indId);
    void addIndent(AddIndentRequest addIndentRequest);
    void cancelIndent(CancelIndentRequest cancelIndentRequest);
    void writeOffIndent(int indId);
    void changeIndentNotes(ChangeIndentNotesRequest changeIndentNotesRequest);
}
