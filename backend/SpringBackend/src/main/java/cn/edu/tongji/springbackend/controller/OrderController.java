package cn.edu.tongji.springbackend.controller;

import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Resource
    private UserController userController;
    @Resource
    private SocietyActivityController societyActivityController;
    @Resource
    private CommunicateController communicateController;
    @Resource
    private OrderService orderService;

    @GetMapping("/userlist/student")
    public ResponseEntity<?> getStudentList(
            @RequestParam("BEGIN_NUMBER") int beginNumber,
            @RequestParam("END_NUMBER") int endNumber) {
        try {
            // 调用 profileService 的 getStudentProfileList 方法，并传递 beginNumber 和 endNumber 参数
            GetStudentPageResponse studentList = userController.getStudentProfileList(beginNumber, endNumber);
            // 返回获取到的学生列表
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            // 处理异常情况并返回适当的响应
            return new ResponseEntity<>("Failed to retrieve student list: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/userlist/society")
    public ResponseEntity<?> getSocietyList(
            @RequestParam("BEGIN_NUMBER") int beginNumber,
            @RequestParam("END_NUMBER") int endNumber) {
        try {
            GetSocietyPageResponse societyList = userController.getSocietyProfileList(beginNumber, endNumber);
            // 返回获取到的学生列表
            return new ResponseEntity<>(societyList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            // 处理异常情况并返回适当的响应
            return new ResponseEntity<>("Failed to retrieve society list: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/society-activitylist")
    public ResponseEntity<?> getSocietyActivityList(
            @RequestParam("SOC_ID") int socId,
            @RequestParam("BEGIN_NUMBER") int beginNumber,
            @RequestParam("END_NUMBER") int endNumber) {
        try {
            GetSocietyActivityListResponse activityList = societyActivityController.getSocietyActivityList(socId, beginNumber, endNumber);
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
            @RequestParam("BEGIN_NUMBER") int beginNumber,
            @RequestParam("END_NUMBER") int endNumber) {
        try {
            GetAppealPageResponse getAppealPageResponse = orderService.getAppealPage(timeOrder, beginNumber, endNumber);

            for (AppealDetailedInfo info : getAppealPageResponse.getAppealList()) {
                String content = info.getCmtContent();

                if (!Objects.equals(content, "")) {
                    int cmtId = Integer.parseInt(content);
                    info.setCmtContent(communicateController.getComment(cmtId));
                }
            }

            return new ResponseEntity<>(getAppealPageResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get appeal page failed", HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<?> setUserProhibitedStatus(
            @RequestParam("USER_ID") int userId,
            @RequestParam("IF_PROHIBITED") boolean ifProhibited) {
        try {
            userController.setUserProhibitedStatus(userId, ifProhibited);
            return new ResponseEntity<>(
                    "successfully " + (ifProhibited ? "prohibit" : "unblock") + " user",
                    HttpStatus.OK
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(
                    (ifProhibited ? "prohibit" : "unblock") + " user failed",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PutMapping("/reg-request")
    public ResponseEntity<?> passRegRequest(@RequestParam("USER_ID") int userId) {
        try {
            userController.passRegRequest(userId);
            return new ResponseEntity<>("successfully pass register request", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("pass register request failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/reg-request")
    public ResponseEntity<?> refuseRegRequest(@RequestParam("USER_ID") int userId) {
        try {
            userController.refuseRegRequest(userId);
            return new ResponseEntity<>("successfully refuse register request", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("refuse register request failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
