package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.Activity;
import cn.edu.tongji.springbackend.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ActivityMapper {
    int insertActivity(Activity activity);
    int getCount();
    int getCountBySocId(@Param("socId") int socId);
    Activity getByActId(@Param("actId") int actId);
    List<Activity> getByPage(@Param("pageSize") int pageSize, @Param("offset") int offset);
    void update(Activity activity);
    List<Activity> getActivityListByRange(@Param("startRow") int startRow, @Param("pageSize") int pageSize);

    List<Activity> getSocActivities(Integer socId, List<String> keyword, String query);

    void updateActivity(Activity activity);

    List<Activity> getSocActivities(Map<String, Object> params);
    List<Activity> getByPageAndSocId(@Param("socId") int socId, @Param("startRow") int startRow, @Param("pageSize") int pageSize);

}
