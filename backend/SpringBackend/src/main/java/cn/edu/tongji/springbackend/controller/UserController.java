package cn.edu.tongji.springbackend.controller;

import cn.edu.tongji.springbackend.model.User;
import cn.edu.tongji.springbackend.mapper.UserMapper;
import cn.edu.tongji.springbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
