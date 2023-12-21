package cn.edu.tongji.springbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.edu.tongji.springbackend.service.KeywordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/keywords")
public class KeywordsController {
    private static final Logger logger = LoggerFactory.getLogger(KeywordsController.class);
    @Autowired
    private KeywordsService keywordsService;

    @GetMapping("/getkeywords")
    public ResponseEntity<Object> getAllKeywords() {
        try {
            logger.info("start to get keywords");
            List<String> keywords = keywordsService.getAllKeywords();
            logger.info("Successfully retrieved keywords: {}", keywords);

            // 成功时返回关键字列表
            return ResponseEntity.ok().body(Map.of("keywords", keywords));
        } catch (Exception e) {
            logger.error("Error while getting keywords", e);

            // 失败时返回失败信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get keywords"));
        }
    }

}
