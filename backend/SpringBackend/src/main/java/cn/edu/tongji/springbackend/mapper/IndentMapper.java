package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.Indent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IndentMapper {
    List<Indent> getByStuId(@Param("stuId") int stuId);
    List<Indent> getByStuIdIndStatus(@Param("stuId") int stuId, @Param("indStatus") int indStatus);
    int add(Indent indent);
    int update(Indent indent);
}
