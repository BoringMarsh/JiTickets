package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.Keywords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface KeywordsMapper {
    List<Keywords> getAllKeywords();
    // Add other methods as needed
}