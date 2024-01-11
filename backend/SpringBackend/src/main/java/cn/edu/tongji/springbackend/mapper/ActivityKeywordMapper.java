package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.ActivityKeyword;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ActivityKeywordMapper {
    List<ActivityKeyword> getById(@Param("id") int id);
    void insertActivityKeyword(ActivityKeyword activityKeyword);
    List<String> getActivityKeywords(Integer actId);
}
