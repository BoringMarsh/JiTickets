package cn.edu.tongji.springbackend.service;

import cn.edu.tongji.springbackend.model.User;
import cn.edu.tongji.springbackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public int save(User user){
        // 如果user没有id则表明是新增
        if(user.getId()==null){
            return userMapper.insert(user);
        }
        // 否则就是更新
        else {
            return userMapper.update(user);
        }
    }

    public Integer deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

}
