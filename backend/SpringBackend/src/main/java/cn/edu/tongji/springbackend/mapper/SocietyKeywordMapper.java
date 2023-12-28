package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.SocietyKeyword;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SocietyKeywordMapper {

    List<String> getSocietyKeywords(Integer socId);

    void insertSocietyKeyword(SocietyKeyword societyKeyword);

    void deleteKeywordsBySocietyId(Integer socId);
}