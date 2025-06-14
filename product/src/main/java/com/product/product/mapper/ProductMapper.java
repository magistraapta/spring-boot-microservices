package com.product.product.mapper;

import org.mapstruct.Mapper;

import com.product.product.dto.ProductDto;
import com.product.product.entity.ProductEntity;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toEntity(ProductDto productRequest);
    ProductDto toDto(ProductEntity productEntity);
}
