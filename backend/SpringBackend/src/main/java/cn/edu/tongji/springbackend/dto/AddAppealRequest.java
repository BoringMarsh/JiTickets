package cn.edu.tongji.springbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddAppealRequest {
    private Integer appMatters;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime appTime;

    private String appContent;
    private Integer userId;
    private Integer actId;
    private Integer cmtId;
    private Integer complainantId;
}
