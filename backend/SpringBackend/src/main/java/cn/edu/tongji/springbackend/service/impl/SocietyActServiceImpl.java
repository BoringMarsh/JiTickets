package cn.edu.tongji.springbackend.service.impl;

import cn.edu.tongji.springbackend.config.FileStorageProperties;
import cn.edu.tongji.springbackend.controller.KeywordsController;
import cn.edu.tongji.springbackend.dto.ActivityDetailedInfo;
import cn.edu.tongji.springbackend.dto.ActivityUpdateRequest;
import cn.edu.tongji.springbackend.dto.SocActivityResponse;
import cn.edu.tongji.springbackend.dto.UploadActReq;
import cn.edu.tongji.springbackend.exceptions.FileStorageException;
import cn.edu.tongji.springbackend.mapper.ActivityImageMapper;
import cn.edu.tongji.springbackend.mapper.ActivityKeywordMapper;
import cn.edu.tongji.springbackend.mapper.ActivityMapper;
import cn.edu.tongji.springbackend.model.*;
import cn.edu.tongji.springbackend.model.ActivityKeyword;
import cn.edu.tongji.springbackend.service.SocietyActivityService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SocietyActServiceImpl implements SocietyActivityService {
    private static final Logger logger = LoggerFactory.getLogger(KeywordsController.class);
    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private ActivityKeywordMapper activityKeywordMapper;
    @Resource
    private ActivityImageMapper activityImageMapper;
    @Autowired
    private FileStorageProperties fileStorageProperties;

    @Override
    @Transactional
    public int uploadActivity(UploadActReq uploadActReq) {
        try {
            logger.info("upload activity service!");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // 将字符串转换为 LocalDateTime 对象
            LocalDateTime regStartTime = LocalDateTime.parse(uploadActReq.getRegStartTime(), formatter);
            LocalDateTime regEndTime = LocalDateTime.parse(uploadActReq.getRegEndTime(), formatter);
            LocalDateTime actTime = LocalDateTime.parse(uploadActReq.getActTime(), formatter);

            Activity activity = Activity.builder()
                    .actName(uploadActReq.getActName())
                    .actIntro(uploadActReq.getActIntro())
                    .actLocation(uploadActReq.getActLocation())
                    .ticketPrice(uploadActReq.getTicketPrice())
                    .uploadTime(LocalDateTime.now())
                    .actCapacity(uploadActReq.getActCapacity())
                    .actLeft(uploadActReq.getActCapacity())
                    .actRating(5.0)
                    .ratingNum(0)
                    .socId(uploadActReq.getSocId())
                    .regStartTime(regStartTime)
                    .regEndTime(regEndTime)
                    .actTime(actTime)
                    .build();

            logger.info("Successfully set activity info: {}", activity);

            int rowsAffected = activityMapper.insertActivity(activity);
            // Check if the insertion was successful
            if (rowsAffected > 0) {
                // Retrieve the generated ID and update the Activity object
                Integer generatedId = activity.getActId();
                activity.setActId(generatedId);
                logger.info("Successfully inserted activity with ID: {}", generatedId);
            } else {
                logger.error("Failed to insert activity");
            }
            
            processActivityKeywords(activity.getActId(), uploadActReq.getKeyword());
            logger.info("Successfully inserted actKeywords with ID: {}", activity.getActId());

            processActivityImages(activity.getActId(), uploadActReq.getBase64ActImages());
            logger.info("Successfully inserted actImages with ID: {}", activity.getActId());

            return activity.getActId();
        } catch (Exception ex)
        {
            logger.error("Error occurred in uploadActivity: " + ex.getMessage(), ex);
            throw ex; // Re-throw the exception if you want to propagate it up the call stack
        }
    }
    private void processActivityKeywords(Integer actId, List<String> actKeywords) {
        if (actKeywords != null) {
            for (String keyword : actKeywords) {
                ActivityKeyword activityKeyword = new ActivityKeyword();
                activityKeyword.setActId(actId);
                activityKeyword.setKeyword(keyword);
                activityKeywordMapper.insertActivityKeyword(activityKeyword);
            }
        }
    }
    private void processActivityImages(Integer actId, List<String> socImageFiles) {
        if (socImageFiles != null) {
            for (String base64Image : socImageFiles) {
                if (base64Image != null && !base64Image.isEmpty()) {
                    String imagePath = saveImage(base64Image);
                    ActivityImage activityImage = new ActivityImage();
                    activityImage.setActId(actId);
                    activityImage.setActImage(imagePath);
                    activityImageMapper.insertActivityImage(activityImage);
                }
            }
        }
    }
    private String saveImage(String base64Image) {
        try {
            // Specify the directory where you want to store images
            String actImageDir = fileStorageProperties.getActImageDir();
            Files.createDirectories(Paths.get(actImageDir));

            // Decode the Base64 string
            byte[] imageBytes = Base64.getDecoder().decode(base64Image.split(",")[1]);

            // Generate a unique filename to avoid collisions
            String uniqueFileName = UUID.randomUUID().toString() + ".jpg"; // Assuming JPEG format
            Path targetLocation = Paths.get(actImageDir, uniqueFileName);

            // Save the decoded bytes to file
            Files.write(targetLocation, imageBytes);

            // Return the relative path
            return actImageDir + uniqueFileName;
        } catch (IOException ex) {
            throw new FileStorageException("Failed to store image file", ex);
        }
    }

    @Override
    public List<ActivityDetailedInfo> getActivityList(int beginNumber, int endNumber) {
        try {
            // 根据起始和结束索引分页查询列表
            int startRow = beginNumber - 1;  // 起始行索引，减1以匹配数据库行数从0开始的情况
            int pageSize = endNumber - beginNumber + 1;  // 检索的数据行数
            List<Activity> activities = activityMapper.getActivityListByRange(beginNumber, endNumber);

            // 处理数据库记录不足的情况
            if (activities.isEmpty()) {
                return new ArrayList<>(); // 返回一个空列表
            }
            // 创建一个存储学生资料响应的列表
            List<ActivityDetailedInfo> activityList = new ArrayList<>();
            // 遍历学生列表并获取个人资料
            for (Activity activity : activities) {
                Integer actId = activity.getActId();
                // 创建活动资料响应对象
                ActivityDetailedInfo activityInfo = new ActivityDetailedInfo();
                activityInfo.setActName(activity.getActName());
                activityInfo.setActIntro(activity.getActIntro());
                activityInfo.setActLocation(activity.getActLocation());
                activityInfo.setTicketPrice(activity.getTicketPrice());
                activityInfo.setUploadTime(activity.getUploadTime());
                activityInfo.setRegStartTime(activity.getRegStartTime());
                activityInfo.setRegEndTime(activity.getRegEndTime());
                activityInfo.setActTime(activity.getActTime());
                activityInfo.setActCapability(activity.getActCapacity());
                activityInfo.setActLeft(activity.getActLeft());
                activityInfo.setActRating(activity.getActRating());
                activityInfo.setRatingNum(activity.getRatingNum());
                activityInfo.setSocId(activity.getSocId());

                // 获取学生的关键字信息并设置到响应对象中
                List<String> activityKeywords = activityKeywordMapper.getActivityKeywords(actId);
                activityInfo.setKeywords(activityKeywords);

                // 查询并读取所有image文件并转换为Base64字符串列表
                List<String> imageBase64List = new ArrayList<>();
                List<ActivityImage> activityImages = activityImageMapper.getActivityImagesByActId(actId);
                for (ActivityImage image : activityImages) {
                    String imageBase64 = readAndConvertToBase64(image.getActImage());
                    imageBase64 = "data:image/jpg;base64," + imageBase64;
                    imageBase64List.add(imageBase64);
                }
                activityInfo.setImages(imageBase64List);

                // 将学生资料添加到响应列表
                activityList.add(activityInfo);
            }
            return activityList;
        } catch (Exception e) {
            // 处理异常情况并返回适当的响应
            logger.error("Failed to retrieve student profiles: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve student profiles: " + e.getMessage());
        }
    }
    // 读取文件并将内容转换为Base64字符串的方法
    private String readAndConvertToBase64(String filePath) {
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
            return Base64.getEncoder().encodeToString(fileBytes);
        } catch (IOException e) {
            // 处理文件读取异常
            logger.error("Error reading and converting file to Base64: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void updateActivity(ActivityUpdateRequest request) {
        // 将请求数据转换为模型类对象
        Activity activity = Activity.builder()
                .actId(request.getActId())
                .actName(request.getActName())
                .actIntro(request.getActIntro())
                .actLocation(request.getActLocation())
                .ticketPrice(request.getTicketPrice())
                .regStartTime(request.getRegStartTime())
                .regEndTime(request.getRegEndTime())
                .actTime(request.getActTime())
                .actCapacity(request.getActCapacity())
                .build();

        // 更新活动基本信息
        activityMapper.updateActivity(activity);

        // 更新活动关键词
        updateActivityKeywords(request.getActId(), request.getActKeywords());

        // 更新活动图片
        updateActivityImages(request.getActId(), request.getBase64ActImages());

    }

    private void updateActivityKeywords(Integer actId, List<String> actKeywords) {
        activityKeywordMapper.deleteActivityKeywords(actId); // Assuming this method exists
        processActivityKeywords(actId, actKeywords);
    }

    private void updateActivityImages(Integer actId, List<String> base64Images) {
        deleteOldActivityImages(actId); // Deletes images from filesystem and database
        processActivityImages(actId, base64Images);
    }

    private void deleteOldActivityImages(Integer actId) {
        List<ActivityImage> existingImages = activityImageMapper.getActivityImagesByActId(actId);
        for (ActivityImage image : existingImages) {
            Path path = Paths.get(image.getActImage());
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                // Handle the exception, e.g., log it
            }
        }
        activityImageMapper.deleteActivityImages(actId); // Assuming this method exists
    }




    @Override
    public List<SocActivityResponse> getSocActivities(@RequestBody ActivitySearchCriteria criteria) {
        try {
            // 首先根据socId, keyword, query获取活动列表
            List<Activity> initialActivities = activityMapper.getSocActivities(criteria.getSocId(),
                    criteria.getKeyword(), criteria.getQuery());
            logger.info("Successfully received records: {}", initialActivities);
            // 根据其他条件进行筛选
            List<Activity> filterActivities = filterActivities(initialActivities, criteria);

            // 转换为ActivityResponse列表
            return convertToActivityResponse(filterActivities);
        } catch (Exception ex) {
            logger.error("Error occurred in getActivities: " + ex.getMessage(), ex);
            throw ex; // Re-throw the exception if you want to propagate it up the call stack
        }
    }

    private List<SocActivityResponse> convertToActivityResponse(List<Activity> activities) {
        List<SocActivityResponse> responses = new ArrayList<>();
        for (Activity activity : activities) {
            SocActivityResponse response = new SocActivityResponse();
            response.setAct_id(activity.getActId());
            response.setAct_name(activity.getActName());
            response.setAct_left(activity.getActLeft());
            response.setUpload_time(activity.getUploadTime().toString());
            response.setRegEnd_time(activity.getRegEndTime().toString());
            response.setTicket_price(activity.getTicketPrice().intValue());

            // 获取并设置关键词和图片
            List<String> keywords = activityKeywordMapper.getActivityKeywords(activity.getActId());
            response.setKeyword(keywords);

            // 获取图片对象列表并转换为Base64字符串列表
            List<ActivityImage> activityImages = activityImageMapper.getActivityImagesByActId(activity.getActId());
            List<String> imageBase64Strings = activityImages.stream()
                    .map(ActivityImage::getActImage)
                    .map(this::readAndConvertToBase64)
                    .map(base64 -> "data:image/jpg;base64," + base64)
                    .collect(Collectors.toList());

            response.setAct_image(imageBase64Strings.get(0));

            responses.add(response);
        }
        return responses;
    }

    private boolean meetsCriteria(Activity activity, ActivitySearchCriteria criteria) {
        // 处理状态条件
        if (criteria.getStatus() != null) {
            int status = determineStatus(activity);
            logger.info("Successfully calculated status: {}", status);
            logger.info("Successfully arrived 1");
            if (criteria.getStatus() != status) return false;
        }

        // 处理uploadTime条件
        if (criteria.getUploadTime() != null) {
            LocalDate uploadDate = LocalDate.parse(criteria.getUploadTime());
            logger.info("Successfully arrived 2");
            if (!activity.getUploadTime().toLocalDate().isEqual(uploadDate)) return false;
        }

        // 处理regEndTime条件
        if (criteria.getRegEndTime() != null) {
            LocalDate regEndDate = LocalDate.parse(criteria.getRegEndTime());
            logger.info("Successfully arrived 3");
            if (!activity.getRegEndTime().toLocalDate().isEqual(regEndDate)) return false;
        }
        logger.info("Successfully arrived 4");
        return true;
    }

    private int determineStatus(Activity activity) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(activity.getRegEndTime())) {
            return -1; // 报名截止
        } else if (activity.getActLeft().equals(0)) {
            return 0; // 报名人满
        } else {
            return 1; // 活动报名中
        }
    }


    private List<Activity> filterActivities(List<Activity> activities, ActivitySearchCriteria criteria) {
        Stream<Activity> filteredStream = activities.stream()
                .filter(activity -> meetsCriteria(activity, criteria));

        // 应用分页
        if (criteria.getPage() != null && criteria.getPageSize() != null) {
            int page = criteria.getPage();
            int pageSize = criteria.getPageSize();
            filteredStream = filteredStream.skip((long) (page - 1) * pageSize)
                    .limit(pageSize);
        }

        return filteredStream.collect(Collectors.toList());
    }


}
