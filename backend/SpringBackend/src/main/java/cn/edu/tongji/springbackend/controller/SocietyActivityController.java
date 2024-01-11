package cn.edu.tongji.springbackend.controller;

import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.service.SocietyActivityService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/society/activity")
public class SocietyActivityController {
    @Resource
    private SocietyActivityService societyActivityService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadActivity(@RequestBody UploadActReq uploadActReq) {
        try {
            int actId = societyActivityService.uploadActivity(uploadActReq);
            return new ResponseEntity<>("successfully upload activity", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("upload activity failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
