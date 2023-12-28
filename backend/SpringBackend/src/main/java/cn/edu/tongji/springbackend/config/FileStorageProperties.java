package cn.edu.tongji.springbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileStorageProperties {

    @Value("${file.upload.society-logo-dir}")
    private String logoUploadDir;

    @Value("${file.upload.society-image-dir}")
    private String imageUploadDir;

    public String getLogoUploadDir() {
        return logoUploadDir;
    }

    public String getImageUploadDir() {
        return imageUploadDir;
    }
}
