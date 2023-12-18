package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.Browse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BrowseMapper {
    int add(Browse browse);
}
