package com.product.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.product.product.dto.ProductResponse;
import com.product.product.entity.Product;
import com.product.product.exc.InsufficientStockException;
import com.product.product.exc.InvalidRequestException;
import com.product.product.exc.ProductNotFoundException;
import com.product.product.mapper.ProductMapper;
import com.product.product.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }  

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public List<ProductResponse> getAllProducts() {
        log.info("Getting all products");
        return productRepository.findAll()
            .stream()
            .map(productMapper::toProductResponse)
            .collect(Collectors.toList());  
    }

    public ProductResponse getProductById(Long id) {
        log.info("Getting product by id: {}", id);
        return productMapper.toProductResponse(productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id)));
    }

    public void updateProduct(Long id, Product product) {
        log.info("Updating product by id: {}", id);
        Product existingProduct = productMapper.toProduct(getProductById(id));
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        log.info("Deleting product by id: {}", id);
        // Check if product exists before deleting
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }

    public void decreaseStock(Long id, Integer quantity) {
        log.info("Decreasing stock for product by id: {}", id);
        if (quantity <= 0) {
            throw new InvalidRequestException("Quantity must be greater than 0");
        }
        
        Product product = productMapper.toProduct(getProductById(id));
        if (product.getQuantity() < quantity) {
            throw new InsufficientStockException(product.getQuantity(), quantity);
        }
        
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }
}
