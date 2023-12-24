package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.Indent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IndentMapper {
    Indent getByIndId(@Param("indId") int indId);
    List<Indent> getByStuIdIndStatus(@Param("stuId") int stuId, @Param("indStatus") int indStatus);
    int stuGetCount(@Param("stuId") int stuId);
    List<Indent> stuGetByPage(
            @Param("stuId") int stuId,
            @Param("pageSize") int pageSize,
            @Param("offset") int offset
    );
    int add(Indent indent);
    int update(Indent indent);
    int getActIdByIndId(@Param("indId") int indId);
}
