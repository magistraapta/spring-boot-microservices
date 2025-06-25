package com.product.product.exc;

public class InsufficientStockException extends RuntimeException {
    
    public InsufficientStockException(String message) {
        super(message);
    }
    
    public InsufficientStockException(Integer available, Integer requested) {
        super("Insufficient stock. Available: " + available + ", Requested: " + requested);
    }
} 