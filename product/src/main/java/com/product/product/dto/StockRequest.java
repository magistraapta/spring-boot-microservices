package com.product.product.dto;

import lombok.Data;

@Data
public class StockRequest {
    private Long productId;
    private Integer quantity;
} 