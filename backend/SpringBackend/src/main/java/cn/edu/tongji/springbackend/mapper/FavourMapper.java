package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.Favour;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FavourMapper {
    List<Favour> getById(@Param("stuId") int stuId);
    int add(Favour favour);
    void delete(Favour favour);
}
