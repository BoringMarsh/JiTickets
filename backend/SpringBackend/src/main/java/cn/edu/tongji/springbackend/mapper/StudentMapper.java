package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper {
    Student getStudentById(Integer stuId);
    void insertStudent(Student student);
    void updateStudent(Student student);
    // Add other methods as needed
}
