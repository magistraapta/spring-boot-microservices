package com.user.user.exc;

public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }

    public static UserExistException forUsername(String username) {
        return new UserExistException("User already exists with username: " + username);
    }
}