package cn.edu.tongji.springbackend.service.impl;

import cn.edu.tongji.springbackend.mapper.KeywordsMapper;
import cn.edu.tongji.springbackend.service.KeywordsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordsServiceImpl implements KeywordsService {
    @Resource
    private KeywordsMapper keywordsMapper;

    public List<String> getAllKeywords() {
        return keywordsMapper.getAllKeywords();
    }
}
