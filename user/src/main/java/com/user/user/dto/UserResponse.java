package com.user.user.dto;

import com.user.user.enums.Role;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String password;
    private Role role;
}
