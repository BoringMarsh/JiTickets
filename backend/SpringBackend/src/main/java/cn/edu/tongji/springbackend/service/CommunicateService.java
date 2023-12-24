package cn.edu.tongji.springbackend.service;

import cn.edu.tongji.springbackend.dto.AddCommentRequest;
import cn.edu.tongji.springbackend.dto.CommentInfo;
import cn.edu.tongji.springbackend.dto.RateActivityRequest;
import cn.edu.tongji.springbackend.dto.ReplyCommentRequest;

import java.util.List;

public interface CommunicateService {
    List<CommentInfo> getCommentByActId(int actId);
    void addComment(AddCommentRequest addCommentRequest);
    void replyComment(ReplyCommentRequest replyCommentRequest);
    void rateActivity(RateActivityRequest rateActivityRequest);
    void changeRating(RateActivityRequest rateActivityRequest);
}
