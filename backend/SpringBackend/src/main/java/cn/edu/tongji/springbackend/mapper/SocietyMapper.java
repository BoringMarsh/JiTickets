package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.Society;
import cn.edu.tongji.springbackend.model.SocietyAdmin;
import cn.edu.tongji.springbackend.model.SocietyImage;
import cn.edu.tongji.springbackend.model.SocietyKeyword;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SocietyMapper {
    void insertSociety(Society society);
    void insertSocietyAdmin(SocietyAdmin societyAdmin);
    void insertSocietyImage(SocietyImage societyImage);
    void insertSocietyKeyword(SocietyKeyword societyKeyword);
}
