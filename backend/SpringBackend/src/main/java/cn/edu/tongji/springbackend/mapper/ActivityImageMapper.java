package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.ActivityImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ActivityImageMapper {
    List<ActivityImage> getById(@Param("id") int id);
}
