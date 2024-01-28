package cn.edu.tongji.springbackend.dto;

import lombok.Data;

@Data
public class BalanceRequest {
    private String username;
    private Double balance;
}
