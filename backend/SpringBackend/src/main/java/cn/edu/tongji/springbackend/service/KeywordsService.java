package cn.edu.tongji.springbackend.service;

import cn.edu.tongji.springbackend.mapper.KeywordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordsService {

    @Autowired
    private KeywordsMapper keywordsMapper;

    public List<String> getAllKeywords() {
        return keywordsMapper.getAllKeywords();
    }
}
