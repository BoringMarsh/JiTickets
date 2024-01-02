package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
    List<Comment> getByActId(@Param("actId") int actId);
    void add(Comment comment);
}
