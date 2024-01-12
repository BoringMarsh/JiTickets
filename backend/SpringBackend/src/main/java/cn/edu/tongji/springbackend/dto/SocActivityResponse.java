package cn.edu.tongji.springbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class SocActivityResponse {
    private Integer act_id;
    private String act_name;
    private Integer act_left;
    private String upload_time;
    private String reg_end_time;
    private Integer ticket_price;
    private List<String> keyword;
    private List<String> act_image;
}
