package app.authentication.auth_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import app.authentication.auth_service.dto.AuthRequest;
import app.authentication.auth_service.dto.UserDto;

@FeignClient(name = "user", url = "http://localhost:8084")
public interface UserClient {

    @GetMapping("/users/{username}")
    ResponseEntity<UserDto> getUserByUsername(@PathVariable String username);

    @PostMapping("/users")
    ResponseEntity<AuthRequest> createUser(@RequestBody AuthRequest request);

}
