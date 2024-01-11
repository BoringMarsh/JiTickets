package cn.edu.tongji.springbackend.controller;

import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.service.OrderService;
import cn.edu.tongji.springbackend.service.ProfileService;
import cn.edu.tongji.springbackend.service.SocietyActivityService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private ProfileService profileService;
    @Resource
    private SocietyActivityService societyActivityService;

    @GetMapping("/userlist/student")
    public ResponseEntity<?> getStudentList(
            @RequestParam(name = "BEGIN_NUMBER", required = false) Integer beginNumber,
            @RequestParam(name = "END_NUMBER", required = false) Integer endNumber) {
        try {
            // 调用 profileService 的 getStudentProfileList 方法，并传递 beginNumber 和 endNumber 参数
            List<GetStudentProfileResponse> studentList = profileService.getStudentProfileList(beginNumber, endNumber);
            // 返回获取到的学生列表
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            // 处理异常情况并返回适当的响应
            return new ResponseEntity<>("Failed to retrieve student list: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/userlist/society")
    public ResponseEntity<?> getSocietyList(
            @RequestParam(name = "BEGIN_NUMBER", required = false) Integer beginNumber,
            @RequestParam(name = "END_NUMBER", required = false) Integer endNumber) {
        try {
            List<GetSocietyProfileResponse> societyList = profileService.getSocietyProfileList(beginNumber, endNumber);
            // 返回获取到的学生列表
            return new ResponseEntity<>(societyList, HttpStatus.OK);
        } catch (Exception e) {
            // 处理异常情况并返回适当的响应
            return new ResponseEntity<>("Failed to retrieve society list: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/activitylist")
    public ResponseEntity<?> getActivityList(
            @RequestParam(name = "BEGIN_NUMBER", required = false) Integer beginNumber,
            @RequestParam(name = "END_NUMBER", required = false) Integer endNumber) {
        try {
            List<ActivityDetailedInfo> activityList = societyActivityService.getActivityList(beginNumber, endNumber);
            // 返回获取到的学生列表
            return new ResponseEntity<>(activityList, HttpStatus.OK);
        } catch (Exception e) {
            // 处理异常情况并返回适当的响应
            return new ResponseEntity<>("Failed to retrieve society list: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/appeal/page")
    public ResponseEntity<?> getAppealPage(
            @RequestParam("TIME_ORDER") int timeOrder,
            @RequestParam("BEGIN_NUM") int beginNum,
            @RequestParam("END_NUM") int endNum) {
        try {
            GetAppealPageResponse getAppealPageResponse = orderService.getAppealPage(timeOrder, beginNum, endNum);
            return new ResponseEntity<>(getAppealPageResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get appeal page failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/appeal/{appId}")
    public ResponseEntity<?> getAppeal(@PathVariable("appId") int appId) {
        try {
            AppealDetailedInfo appealDetailedInfo = orderService.getAppeal(appId);
            return new ResponseEntity<>(appealDetailedInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get appeal failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/appeal")
    public ResponseEntity<?> addAppeal(@RequestParam("appealImages") List<MultipartFile> appealImages, AddAppealRequest addAppealRequest) {
        try {
            addAppealRequest.setAppealImages(appealImages);
            orderService.addAppeal(addAppealRequest);
            return new ResponseEntity<>("successfully add appeal", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("add appeal failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/prohibit")
    public ResponseEntity<?> setUserProhibitedStatus(@RequestBody SetUserProhibitedStatusRequest setUserProhibitedStatusRequest) {
        try {
            orderService.setUserProhibitedStatus(setUserProhibitedStatusRequest);
            return new ResponseEntity<>(
                    "successfully " + (setUserProhibitedStatusRequest.getIfProhibited() ? "prohibit" : "unblock") + " user",
                    HttpStatus.OK
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(
                    (setUserProhibitedStatusRequest.getIfProhibited() ? "prohibit" : "unblock") + " user failed",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
