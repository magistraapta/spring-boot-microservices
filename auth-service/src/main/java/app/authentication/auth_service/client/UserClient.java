package app.authentication.auth_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import app.authentication.auth_service.dto.AuthRequest;
import app.authentication.auth_service.dto.UserDto;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserClient {

    @GetMapping("/users/by-username")
    ResponseEntity<UserDto> getUserByUsername(@RequestParam String username);

    @PostMapping("/users")
    ResponseEntity<UserDto> createUser(@RequestBody AuthRequest request);

}
