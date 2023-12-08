package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.Appeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AppealMapper {
    Appeal getById(@Param("id") int id);
    int getCount();
    List<Appeal> getByPage(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
    int add(Appeal appeal);
}
