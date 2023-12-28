package cn.edu.tongji.springbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String message;
    private Integer id;
    private String username;
    private String campus;
    private Integer accountStatus;
    private Double balance;
    private Integer role;
}

