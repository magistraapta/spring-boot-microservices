package com.user.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.user.dto.UserMapper;
import com.user.user.dto.UserRequest;
import com.user.user.entity.User;
import com.user.user.enums.Role;
import com.user.user.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserRequest userRequest) throws Exception {
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new Exception("User already exists");
        }
        User user = userMapper.toEntity(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) throws Exception {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new Exception("User not found");
        }
        return user;
    }

    public User getUserById(Long id) throws Exception {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new Exception("User not found");
        }
        return user;
    }

    public void deleteUser(Long id) throws Exception {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new Exception("User not found");
        }
        userRepository.delete(user);
    }
    
}
