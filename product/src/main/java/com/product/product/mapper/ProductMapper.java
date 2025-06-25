package com.product.product.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import com.product.product.dto.ProductResponse;
import com.product.product.entity.Product;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ProductMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "quantity", source = "quantity")
    ProductResponse toProductResponse(Product product);

    Product toProduct(ProductResponse productResponse);

    
}
