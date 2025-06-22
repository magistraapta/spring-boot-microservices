package com.user.user.dto;

import com.user.user.enums.Role;

import lombok.Data;

@Data
public class UserResponse {
    private String message;
    private String username;
    private Role role;
}
