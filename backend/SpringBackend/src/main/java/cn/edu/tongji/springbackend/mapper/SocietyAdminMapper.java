package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.SocietyAdmin;
import cn.edu.tongji.springbackend.model.SocietyKeyword;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SocietyAdminMapper {

    List<SocietyAdmin> getSocietyAdmins(Integer socId);
    void deleteAdminsBySocietyId(Integer socId);
}