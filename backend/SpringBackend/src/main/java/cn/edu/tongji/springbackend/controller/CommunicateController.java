package cn.edu.tongji.springbackend.controller;

import cn.edu.tongji.springbackend.dto.AddCommentRequest;
import cn.edu.tongji.springbackend.dto.CommentInfo;
import cn.edu.tongji.springbackend.dto.RateActivityRequest;
import cn.edu.tongji.springbackend.dto.ReplyCommentRequest;
import cn.edu.tongji.springbackend.service.CommunicateService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/communicate")
public class CommunicateController {
    @Resource
    private CommunicateService communicateService;

    @GetMapping("/comment/activity/{actId}")
    public ResponseEntity<?> getCommentByActId(@PathVariable("actId") int actId) {
        try {
            List<CommentInfo> commentInfos = communicateService.getCommentByActId(actId);
            return new ResponseEntity<>(commentInfos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get comment by id failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<?> addComment(@RequestBody AddCommentRequest addCommentRequest) {
        try {
            communicateService.addComment(addCommentRequest);
            return new ResponseEntity<>("successfully add comment", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("add comment failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/comment/reply")
    public ResponseEntity<?> replyComment(@RequestBody ReplyCommentRequest replyCommentRequest) {
        try {
            communicateService.replyComment(replyCommentRequest);
            return new ResponseEntity<>("successfully reply comment", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("reply comment failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/rate")
    public ResponseEntity<?> rateActivity(@RequestBody RateActivityRequest rateActivityRequest) {
        try {
            communicateService.rateActivity(rateActivityRequest);
            return new ResponseEntity<>("successfully rate activity", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("rate activity failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/rate/change")
    public ResponseEntity<?> changeRating(@RequestBody RateActivityRequest rateActivityRequest) {
        try {
            communicateService.changeRating(rateActivityRequest);
            return new ResponseEntity<>("successfully change rating", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("change rating failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
