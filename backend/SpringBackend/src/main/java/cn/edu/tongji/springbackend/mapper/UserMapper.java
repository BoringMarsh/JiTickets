package cn.edu.tongji.springbackend.mapper;

import cn.edu.tongji.springbackend.model.User;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface UserMapper {
    User getUserById(Integer userId);
    int insertUser(User user);
    User getUserByUsername(@Param("username") String username);
}
