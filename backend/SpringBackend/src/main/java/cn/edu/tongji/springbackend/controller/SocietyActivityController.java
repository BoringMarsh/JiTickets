package cn.edu.tongji.springbackend.controller;

import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.model.Activity;
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
            logger.info("Successfully received request: {}", uploadActReq);
            int actId = societyActivityService.uploadActivity(uploadActReq);
            return new ResponseEntity<>("successfully upload activity", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("upload activity failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/activities")
    public ResponseEntity<?> getSocActivities(
            @RequestParam(name = "socId") int socId,
            @RequestParam(name = "status") int status,
            @RequestParam(name = "order") int order,
            @RequestParam(name = "keyword") List<String> keyword,
            @RequestParam(name = "query") String query,
            @RequestParam(name = "upload_time") String uploadTime,
            @RequestParam(name = "reg_end_time") String regEndTime,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "pageSize") int pageSize
    ) {
        try {
            // 调用 SocietyActivityService 中的方法来获取社团活动数据
            List<Activity> activities = societyActivityService.getSocActivities(socId, status, order,
                    keyword, query, uploadTime, regEndTime, page, pageSize);
            // 返回获取的社团活动数据
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            // 处理异常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

}
