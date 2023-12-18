package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.AppealImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AppealImageMapper {
    List<AppealImage> getById(@Param("id") int id);
}
