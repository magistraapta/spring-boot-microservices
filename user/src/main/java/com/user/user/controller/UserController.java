package com.user.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.user.dto.UserMapper;
import com.user.user.dto.UserRequest;
import com.user.user.entity.User;
import com.user.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users.stream().map(userMapper::toResponse).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        try {
            User user = userService.createUser(userRequest);
            return ResponseEntity.ok(userMapper.toResponse(user));
        } catch (Exception e) {
            if (e.getMessage().equals("User already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(userMapper.toResponse(user));
        } catch (Exception e) {
            if (e.getMessage().equals("User not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving user");
        }
    }

    @GetMapping("/by-username")
    public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
        try {
            User user = userService.getUserByUsername(username);
            return ResponseEntity.ok(userMapper.toResponse(user));
        } catch (Exception e) {
            if (e.getMessage().equals("User not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving user");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            if (e.getMessage().equals("User not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }
}
