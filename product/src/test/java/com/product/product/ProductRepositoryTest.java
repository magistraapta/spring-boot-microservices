package com.product.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.product.product.entity.ProductEntity;
import com.product.product.repository.ProductRepository;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveProduct() {
        ProductEntity product = new ProductEntity();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);
        product.setQuantity(10);

        ProductEntity savedProduct = productRepository.save(product);
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getDescription(), savedProduct.getDescription());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        assertEquals(product.getQuantity(), savedProduct.getQuantity());
    }

    @Test
    public void testFindAll() {
        List<ProductEntity> products = productRepository.findAll();
        assertEquals(0, products.size());
    }

    @Test
    public void testFindById() {
        ProductEntity product = new ProductEntity();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);
        product.setQuantity(10);

        ProductEntity savedProduct = productRepository.save(product);
        ProductEntity foundProduct = productRepository.findById(savedProduct.getId()).orElse(null);
        assertEquals(savedProduct.getId(), foundProduct.getId());
        assertEquals(savedProduct.getName(), foundProduct.getName());
        assertEquals(savedProduct.getDescription(), foundProduct.getDescription());
        assertEquals(savedProduct.getPrice(), foundProduct.getPrice());
        assertEquals(savedProduct.getQuantity(), foundProduct.getQuantity());
    }

    @Test
    public void testDeleteProduct() {
        ProductEntity product = new ProductEntity();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);
        product.setQuantity(10);
    }

    @Test
    public void testFindByIdNotFound() {
        ProductEntity product = productRepository.findById(1L).orElse(null);
        assertNull(product);
    }
}
