package com.order.order.client;

import lombok.Data;

@Data
public class StockRequest {
    private Long productId;
    private Integer quantity;
} 