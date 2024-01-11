package cn.edu.tongji.springbackend.service.impl;

import cn.edu.tongji.springbackend.config.FileStorageProperties;
import cn.edu.tongji.springbackend.controller.KeywordsController;
import cn.edu.tongji.springbackend.dto.ActivityDetailedInfo;
import cn.edu.tongji.springbackend.dto.GetSocietyProfileResponse;
import cn.edu.tongji.springbackend.dto.UploadActReq;
import cn.edu.tongji.springbackend.exceptions.FileStorageException;
import cn.edu.tongji.springbackend.mapper.ActivityImageMapper;
import cn.edu.tongji.springbackend.mapper.ActivityKeywordMapper;
import cn.edu.tongji.springbackend.mapper.ActivityMapper;
import cn.edu.tongji.springbackend.mapper.UserMapper;
import cn.edu.tongji.springbackend.model.*;
import cn.edu.tongji.springbackend.model.ActivityKeyword;
import cn.edu.tongji.springbackend.service.SocietyActivityService;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

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
                    .socId(uploadActReq.getSocID())
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
            
            processActivityKeywords(activity.getActId(), uploadActReq.getActKeywords());
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
}
