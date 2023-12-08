package cn.edu.tongji.springbackend.controller;

import cn.edu.tongji.springbackend.dto.AddAppealRequest;
import cn.edu.tongji.springbackend.dto.GetAppealPageResponse;
import cn.edu.tongji.springbackend.dto.SetUserProhibitedStatusRequest;
import cn.edu.tongji.springbackend.model.Appeal;
import cn.edu.tongji.springbackend.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    @GetMapping("/appeal/{page}")
    public ResponseEntity<?> getAppealPage(@PathVariable("page") int page) {
        try {
            GetAppealPageResponse getAppealPageResponse = orderService.getAppealPage(page);
            return new ResponseEntity<>(getAppealPageResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get appeal page " + page + " failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/appeal")
    public ResponseEntity<?> addAppeal(@RequestBody AddAppealRequest addAppealRequest) {
        try {
            Appeal appeal = orderService.addAppeal(addAppealRequest);
            return new ResponseEntity<>(appeal, HttpStatus.OK);
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
