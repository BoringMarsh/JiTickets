package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.User;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface UserMapper {
    User getUserById(Integer userId);
    void insertUser(User user);
}
