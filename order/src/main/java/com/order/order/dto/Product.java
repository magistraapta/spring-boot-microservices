package com.order.order.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
}
