package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.StudentKeyword;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentKeywordMapper {
    List<String> getStudentKeywords(Integer stuId);
    void insertStudentKeyword(StudentKeyword studentKeyword);
    void deleteKeywordsByStudentId(Integer stuId);
}