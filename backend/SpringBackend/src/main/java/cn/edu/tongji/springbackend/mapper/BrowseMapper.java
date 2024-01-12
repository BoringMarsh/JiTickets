package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.Browse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BrowseMapper {
    List<Browse> getByBrowserId(@Param("browserId") int browserId);
    void add(Browse browse);
}
