package com.example.springbackend.mapper;

import com.example.springbackend.entity.User;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> findAll();

    @Insert("insert into user(username,password,email,phone,nickname,address) " +
            "VALUES(#{username},#{password},#{email},#{phone},#{nickname},#{address});")
    int insert(User user);

    @Update("update user set username=#{username},password=#{password}," +
            "nickname=#{nickname},email=#{email},phone=#{phone},address=#{address} where id=#{id}")
    int update(User user);

    @Delete("delete from user where id=#{id}")
    int deleteById(@Param("id") Integer id);



}
