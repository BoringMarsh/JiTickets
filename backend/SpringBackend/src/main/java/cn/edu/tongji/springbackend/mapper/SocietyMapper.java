package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SocietyMapper {
    Society getSocietyById(Integer socId);
    void insertSociety(Society society);
    void insertSocietyAdmin(SocietyAdmin societyAdmin);
    void insertSocietyImage(SocietyImage societyImage);
    void insertSocietyKeyword(SocietyKeyword societyKeyword);
    List<SocietyImage> getSocietyImagesBySocietyId(Integer socId);
    void updateSociety(Society society);
    List<Society> getSocietyListByRange(@Param("startRow") int startRow, @Param("pageSize") int pageSize);
}
