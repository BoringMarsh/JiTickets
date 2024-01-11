package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.Activity;
import cn.edu.tongji.springbackend.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ActivityMapper {
    int insertActivity(Activity activity);
    int getCount();
    Activity getByActId(@Param("actId") int actId);
    List<Activity> getByPage(@Param("pageSize") int pageSize, @Param("offset") int offset);
    void update(Activity activity);

    List<Activity> getActivityListByRange(@Param("startRow") int startRow, @Param("pageSize") int pageSize);
}
