package com.example.springbackend.entity;

import lombok.Data;


@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String nickname;
    private String address;
}
