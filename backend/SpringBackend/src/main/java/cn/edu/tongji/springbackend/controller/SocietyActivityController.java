package cn.edu.tongji.springbackend.controller;

import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.model.Activity;
import cn.edu.tongji.springbackend.model.ActivitySearchCriteria;
import cn.edu.tongji.springbackend.service.SocietyActivityService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/society/activity")
public class SocietyActivityController {
    private static final Logger logger = LoggerFactory.getLogger(KeywordsController.class);
    @Resource
    private SocietyActivityService societyActivityService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadActivity(@RequestBody UploadActReq uploadActReq) {
        try {
            logger.info("start to upload activity");
            logger.info("Successfully received request");
            int actId = societyActivityService.uploadActivity(uploadActReq);
            return new ResponseEntity<>("successfully upload activity", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("upload activity failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateActivity(@RequestBody ActivityUpdateRequest request) {
        try {
            societyActivityService.updateActivity(request);
            return ResponseEntity.ok().body("Activity updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/activities")
    public ResponseEntity<?> getSocActivities(@RequestBody ActivitySearchCriteria criteria) {
        try {
            logger.info("start to upload activity");
            logger.info("Successfully received request: {}", criteria);
            // 调用 SocietyActivityService 中的方法来获取社团活动数据
            List<SocActivityResponse> activities = societyActivityService.getSocActivities(criteria);
            // 返回获取的社团活动数据
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            // 处理异常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

}
