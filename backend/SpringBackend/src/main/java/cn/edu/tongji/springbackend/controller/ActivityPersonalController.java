package cn.edu.tongji.springbackend.controller;

import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.exceptions.ActivityFullException;
import cn.edu.tongji.springbackend.model.Browse;
import cn.edu.tongji.springbackend.service.ActivityPersonalService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity-personal")
public class ActivityPersonalController {
    @Resource
    private ActivityPersonalService activityPersonalService;

    @GetMapping("/activity/page/{page}")
    public ResponseEntity<?> getActivityPage(@PathVariable("page") int page) {
        try {
            GetActivityPageResponse activityShortInfos = activityPersonalService.getActivityPage(page);
            return new ResponseEntity<>(activityShortInfos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get activity page " + page + " failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/activity/{actId}")
    public ResponseEntity<?> getActivity(@PathVariable("actId") int actId) {
        try {
            ActivityDetailedInfo activityDetailedInfo = activityPersonalService.getActivity(actId);
            return new ResponseEntity<>(activityDetailedInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get activity failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/browse")
    public ResponseEntity<?> getBrowse(@RequestParam("userId") int userId) {
        try {
            List<Browse> browses = activityPersonalService.getBrowse(userId);
            return new ResponseEntity<>(browses, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get browse failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/browse")
    public ResponseEntity<?> addBrowse(@RequestBody AddBrowseRequest addBrowseRequest) {
        try {
            activityPersonalService.addBrowse(addBrowseRequest);
            return new ResponseEntity<>("successfully add browse", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("add browse failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/favour/{stuId}")
    public ResponseEntity<?> getFavour(@PathVariable("stuId") int stuId) {
        try {
            List<ActivityShortInfo> activityShortInfos = activityPersonalService.getFavour(stuId);
            return new ResponseEntity<>(activityShortInfos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get favour failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/favour")
    public ResponseEntity<?> addFavour(@RequestParam("stuId") int stuId, @RequestParam("actId") int actId) {
        try {
            activityPersonalService.addFavour(stuId, actId);
            return new ResponseEntity<>("successfully add favour", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("add favour failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/favour")
    public ResponseEntity<?> deleteFavour(@RequestParam("stuId") int stuId, @RequestParam("actId") int actId) {
        try {
            activityPersonalService.deleteFavour(stuId, actId);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("delete favour failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/indent/page")
    public ResponseEntity<?> getIndentPage(@RequestBody GetIndentPageRequest getIndentPageRequest) {
        try {
            GetIndentPageResponse getAppealPageResponse = activityPersonalService.getIndentPage(getIndentPageRequest);
            return new ResponseEntity<>(getAppealPageResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get indent page failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/indent/{indId}")
    public ResponseEntity<?> getIndent(@PathVariable("indId") int indId) {
        try {
            IndentDetailedInfo indentDetailedInfo = activityPersonalService.getIndent(indId);
            return new ResponseEntity<>(indentDetailedInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get indent failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/indent")
    public ResponseEntity<?> addIndent(@RequestBody AddIndentRequest addIndentRequest) {
        try {
            activityPersonalService.addIndent(addIndentRequest);
            return new ResponseEntity<>("successfully add indent", HttpStatus.OK);
        } catch (ActivityFullException e) {
            return new ResponseEntity<>("add indent failed: " + e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("add indent failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/indent/cancel")
    public ResponseEntity<?> cancelIndent(@RequestBody CancelIndentRequest cancelIndentRequest) {
        try {
            activityPersonalService.cancelIndent(cancelIndentRequest);
            return new ResponseEntity<>("successfully cancel indent", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("cancel indent failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/indent/write-off/{indId}")
    public ResponseEntity<?> writeOffIndent(@PathVariable("indId") int indId) {
        try {
            activityPersonalService.writeOffIndent(indId);
            return new ResponseEntity<>("successfully write off indent", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("write off indent failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/indent/notes")
    public ResponseEntity<?> changeIndentNotes(@RequestBody ChangeIndentNotesRequest changeIndentNotesRequest) {
        try {
            activityPersonalService.changeIndentNotes(changeIndentNotesRequest);
            return new ResponseEntity<>("successfully change indent notes", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("change indent notes failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
