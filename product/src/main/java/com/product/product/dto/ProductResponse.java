package com.product.product.dto;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
}
