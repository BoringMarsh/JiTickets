package cn.edu.tongji.springbackend.model;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String campus;
    private Integer loginStatus;
    private Integer accountStatus;
    private Double balance;
    private String payPassword;
    private LocalDateTime regTime;
    private Integer role;
}

