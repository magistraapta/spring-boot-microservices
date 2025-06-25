package com.order.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.order.order.dto.UserDto;

@FeignClient(name = "user-service", url = "${user.url}")
public interface UserClient {
    @GetMapping("/users/{id}")
    UserDto getUserById(@PathVariable Long id);

    @GetMapping("/users/auth/get-by-username")
    UserDto getUserByUsername(@RequestParam String username);
}
