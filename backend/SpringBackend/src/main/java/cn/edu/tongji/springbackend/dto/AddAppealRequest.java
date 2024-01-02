package cn.edu.tongji.springbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddAppealRequest {
    private Integer appMatters;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime appTime;

    private String appContent;
    private Integer userId;
    private Integer actId;
    private Integer cmtId;
    private Integer complainantId;
    private List<MultipartFile> appealImages = null;
}
