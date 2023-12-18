package cn.edu.tongji.springbackend.controller;

import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.service.ActivityPersonalService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity_personal")
public class ActivityPersonalController {
    @Resource
    private ActivityPersonalService activityPersonalService;

    @GetMapping("/activity/page/{page}")
    public ResponseEntity<?> getActivityPage(@PathVariable("page") int page) {
        try {
            List<ActivityShortInfo> activityShortInfos = activityPersonalService.getActivityPage(page);
            return new ResponseEntity<>(activityShortInfos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get activity page " + page + " failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/activity/{id}")
    public ResponseEntity<?> getActivity(@PathVariable("id") int id) {
        try {
            ActivityDetailedInfo activityDetailedInfo = activityPersonalService.getActivity(id);
            return new ResponseEntity<>(activityDetailedInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("get activity failed", HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<?> addFavour(@RequestBody FavourRequest favourRequest) {
        try {
            activityPersonalService.addFavour(favourRequest);
            return new ResponseEntity<>("successfully add favour", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("add favour failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/favour")
    public ResponseEntity<?> deleteFavour(@RequestBody FavourRequest favourRequest) {
        try {
            activityPersonalService.deleteFavour(favourRequest);
            return new ResponseEntity<>("successfully delete favour", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("delete favour failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/indent")
    public ResponseEntity<?> addIndent(@RequestBody AddIndentRequest addIndentRequest) {
        try {
            activityPersonalService.addIndent(addIndentRequest);
            return new ResponseEntity<>("successfully add indent", HttpStatus.OK);
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
            return new ResponseEntity<>("add indent failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/indent/write_off/{indId}")
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

    @PutMapping("/indent/rating")
    public ResponseEntity<?> changeIndentRating(@RequestBody ChangeIndentRatingRequest changeIndentRatingRequest) {
        try {
            activityPersonalService.changeIndentRating(changeIndentRatingRequest);
            return new ResponseEntity<>("successfully change indent rating", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("change indent rating failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
