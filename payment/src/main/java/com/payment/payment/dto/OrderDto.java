package com.payment.payment.dto;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Long userId;
    private String paymentStatus;
} 