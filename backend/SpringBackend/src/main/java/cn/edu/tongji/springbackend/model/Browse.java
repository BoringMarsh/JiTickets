package cn.edu.tongji.springbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Browse {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime broTimeStart;

    private Integer actId;
    private Integer browserId;
    private Boolean whetherBuy;
}
