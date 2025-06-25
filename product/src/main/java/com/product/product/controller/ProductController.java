package com.product.product.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.product.product.dto.ProductResponse;
import com.product.product.entity.Product;
import com.product.product.service.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        productService.createProduct(product);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable @Min(1) Long id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable @Min(1) Long id, @Valid @RequestBody Product product) {
        productService.updateProduct(id, product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable @Min(1) Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/decrease")
    public ResponseEntity<Map<String, Object>> decreaseStock(
            @PathVariable @Min(1) Long id, 
            @RequestParam(required = true) @Min(1) Integer quantity) {
        productService.decreaseStock(id, quantity);
        return ResponseEntity.ok(Map.of(
            "message", "Product stock decreased successfully",
            "productId", id,
            "decreasedQuantity", quantity
        ));
    }
}
