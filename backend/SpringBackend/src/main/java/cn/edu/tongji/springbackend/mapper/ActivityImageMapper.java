package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.ActivityImage;
import cn.edu.tongji.springbackend.model.ActivityKeyword;
import cn.edu.tongji.springbackend.model.SocietyImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ActivityImageMapper {
    void insertActivityImage(ActivityImage activityImage);
    List<ActivityImage> getById(@Param("id") int id);
    List<ActivityImage> getActivityImagesByActId(Integer actId);
}
