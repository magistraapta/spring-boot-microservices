package com.product.product.exc;

public class InvalidRequestException extends RuntimeException {
    
    public InvalidRequestException(String message) {
        super(message);
    }
} 