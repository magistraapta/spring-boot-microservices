package app.authentication.auth_service.dto;

import app.authentication.auth_service.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Role role;
}
