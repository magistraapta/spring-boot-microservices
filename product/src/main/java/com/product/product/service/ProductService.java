package com.product.product.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.product.product.entity.Product;
import com.product.product.repository.ProductRepository;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }  

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        log.info("Getting all products");
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        log.info("Getting product by id: {}", id);
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void updateProduct(Long id, Product product) {
        log.info("Updating product by id: {}", id);
        Product existingProduct = getProductById(id);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        log.info("Deleting product by id: {}", id);
        productRepository.deleteById(id);
    }

    public void decreaseStock(Long id, Integer quantity) {
        log.info("Decreasing stock for product by id: {}", id);
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        
        Product product = getProductById(id);
        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock. Available: " + product.getQuantity() + ", Requested: " + quantity);
        }
        
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }
}
