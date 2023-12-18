package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ActivityMapper {
    int getCount();
    Activity getById(@Param("id") int id);
    List<Activity> getByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}
