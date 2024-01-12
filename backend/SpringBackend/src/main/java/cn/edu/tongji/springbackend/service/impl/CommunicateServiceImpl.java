package cn.edu.tongji.springbackend.service.impl;

import cn.edu.tongji.springbackend.dto.AddCommentRequest;
import cn.edu.tongji.springbackend.dto.CommentInfo;
import cn.edu.tongji.springbackend.dto.ReplyCommentRequest;
import cn.edu.tongji.springbackend.mapper.CommentMapper;
import cn.edu.tongji.springbackend.model.Comment;
import cn.edu.tongji.springbackend.service.CommunicateService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommunicateServiceImpl implements CommunicateService {
    @Resource
    private CommentMapper commentMapper;

    private void getCommentRecursive(List<CommentInfo> list, final Map<Integer, List<Comment>> commentMap, final int fatherId) {
        List<Comment> comments = commentMap.get(fatherId);
        if (comments == null)
            return;

        for (Comment comment : comments) {
            List<CommentInfo> children = new ArrayList<>();
            getCommentRecursive(children, commentMap, comment.getCmtId());  //获取孩子集合

            list.add(new CommentInfo(
                    comment.getCmtId(),
                    comment.getCmtContent(),
                    comment.getCmtTime(),
                    comment.getUserId(),
                    children
            ));
        }
    }

    @Override
    public List<CommentInfo> getCommentByActId(int actId) {
        Map<Integer, List<Comment>> commentMap = new HashMap<>();  //父评论id到子评论的映射
        List<CommentInfo> ret = new ArrayList<>();

        for (Comment comment : commentMapper.getByActId(actId)) {
            List<Comment> value = commentMap.get(comment.getCmtFather());

            if (value == null) {
                value = new ArrayList<>();
                value.add(comment);
                commentMap.put(comment.getCmtFather(), value);
            }
            else {
                value.add(comment);
            }
        }

        getCommentRecursive(ret, commentMap, 0);
        return ret;
    }

    @Override
    public String getCommentByCmtId(int cmtId) {
        return commentMapper.getByCmtId(cmtId).getCmtContent();
    }

    @Override
    public void addComment(AddCommentRequest addCommentRequest) {
        commentMapper.add(Comment.builder()
                .cmtFather(0)
                .cmtContent(addCommentRequest.getCmtContent())
                .cmtTime(addCommentRequest.getCmtTime())
                .actId(addCommentRequest.getActId())
                .userId(addCommentRequest.getUserId())
                .build()
        );
    }

    @Override
    public void replyComment(ReplyCommentRequest replyCommentRequest) {
        commentMapper.add(Comment.builder()
                .cmtFather(replyCommentRequest.getCmtFather())
                .cmtContent(replyCommentRequest.getCmtContent())
                .cmtTime(replyCommentRequest.getCmtTime())
                .actId(replyCommentRequest.getActId())
                .userId(replyCommentRequest.getUserId())
                .build()
        );
    }
}
