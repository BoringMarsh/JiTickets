package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.StudentKeyword;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentKeywordMapper {
    List<StudentKeyword> getStudentKeywords(Integer stuId);

    void insertStudentKeyword(StudentKeyword studentKeyword);

    void updateStudentKeyword(StudentKeyword studentKeyword);
    // Add other methods as needed
}