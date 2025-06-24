package app.authentication.auth_service.dto;

import lombok.Getter;

@Getter
public class AuthRequest {
    private String username;
    private String password;
}
