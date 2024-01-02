package cn.edu.tongji.springbackend.config;

import cn.edu.tongji.springbackend.controller.KeywordsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileStorageProperties {
    private static final Logger logger = LoggerFactory.getLogger(KeywordsController.class);

    //@Value("${file.upload.society-logo-dir}")
    //private String logoUploadDir;

    //@Value("${file.upload.society-image-dir}")
    //private String imageUploadDir;
    // 直接硬编码路径，而不是使用 @Value 注解
    private String logoUploadDir = "D:/软件工程项目/backend/upload_images/society_logo/";
    private String imageUploadDir = "D:/软件工程项目/backend/upload_images/society_image/";


    @Value("${file.upload.appeal-image-dir}")
    private String appealImageDir;

    public String getLogoUploadDir() {
        logger.info("Successfully get dir");
        return logoUploadDir;
    }

    public String getImageUploadDir() {
        return imageUploadDir;
    }

    public String getAppealImageDir() {
        return appealImageDir;
    }
}
