package cn.edu.tongji.springbackend.service.impl;

import cn.edu.tongji.springbackend.config.FileStorageProperties;
import cn.edu.tongji.springbackend.dto.*;
import cn.edu.tongji.springbackend.exceptions.FileStorageException;
import cn.edu.tongji.springbackend.mapper.AppealImageMapper;
import cn.edu.tongji.springbackend.mapper.AppealMapper;
import cn.edu.tongji.springbackend.model.Appeal;
import cn.edu.tongji.springbackend.model.AppealImage;
import cn.edu.tongji.springbackend.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private AppealMapper appealMapper;
    @Resource
    private AppealImageMapper appealImageMapper;
    @Resource
    private FileStorageProperties fileStorageProperties;

    @Override
    public GetAppealPageResponse getAppealPage(int timeOrder, int beginNumber, int endNumber) throws IOException {
        List<AppealDetailedInfo> appealDetailedInfos = new ArrayList<>();

        for (Appeal appeal : appealMapper.getByPage(timeOrder, endNumber - beginNumber + 1, beginNumber - 1)) {
            List<String> images = new ArrayList<>();

            for (AppealImage image : appealImageMapper.getById(appeal.getAppId())) {
                images.add(getImage(image.getAppImage()));
            }

            appealDetailedInfos.add(new AppealDetailedInfo(
                    appeal.getAppId(),
                    appeal.getAppTime(),
                    appeal.getAppMatters(),
                    appeal.getAppContent(),
                    appeal.getUserId(),
                    appeal.getActId(),
                    appeal.getCmtId(),
                    appeal.getCmtId() == null ? "" : appeal.getCmtId().toString(),
                    appeal.getComplainantId(),
                    images
            ));
        }

        return new GetAppealPageResponse(
                appealMapper.getCount(),
                appealDetailedInfos
        );
    }

    private String saveImage(MultipartFile appealImage) {
        try {
            // Specify the directory where you want to store images
            String appealImageDir = fileStorageProperties.getAppealImageDir();
            // Create the directory if it doesn't exist
            Files.createDirectories(Paths.get(appealImageDir));
            // Get the original file name
            String originalFileName = StringUtils.cleanPath(appealImage.getOriginalFilename());
            // Generate a unique filename to avoid collisions
            String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;
            // Create the file path
            Path targetLocation = Paths.get(appealImageDir, uniqueFileName);
            // Copy the file to the target location
            Files.copy(appealImage.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            // Return the relative path
            return appealImageDir + uniqueFileName;
        } catch (IOException e) {
            throw new FileStorageException("Failed to store image file", e);
        }
    }

    private String getImage(String filePath) throws IOException {
        byte[] imageBytes = Files.readAllBytes(Paths.get(filePath));
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    private void processAppealImages(int appId, List<MultipartFile> appealImages) {
        if (appealImages != null) {
            for (MultipartFile image : appealImages) {
                String imagePath = saveImage(image);
                AppealImage appealImage = AppealImage.builder()
                        .appId(appId)
                        .appImage(imagePath)
                        .build();

                appealImageMapper.add(appealImage);
            }
        }
    }

    @Override
    @Transactional
    public void addAppeal(AddAppealRequest addAppealRequest) {
        Appeal appeal = Appeal.builder()
                .appTime(addAppealRequest.getAppTime())
                .appMatters(addAppealRequest.getAppMatters())
                .appContent(addAppealRequest.getAppContent())
                .userId(addAppealRequest.getUserId())
                .actId(addAppealRequest.getActId())
                .cmtId(addAppealRequest.getCmtId())
                .complainantId(addAppealRequest.getComplainantId())
                .build();

        appealMapper.add(appeal);
        processAppealImages(appeal.getAppId(), addAppealRequest.getAppealImages());
    }
}
