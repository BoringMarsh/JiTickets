package com.example.springbackend.controller;

import com.example.springbackend.entity.User;
import com.example.springbackend.service.UserService;
import com.example.springbackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @GetMapping("/get_test")
    public List<User> index(){
        return userMapper.findAll();
    }

    @PostMapping("/post_test")
    public Integer save(@RequestBody User user){
        // return userMapper.insert(user);
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public Integer deleteById(@PathVariable Integer id){
        return  userService.deleteById(id);
    }

}
