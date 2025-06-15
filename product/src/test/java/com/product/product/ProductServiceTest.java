package com.product.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.product.product.dto.ProductDto;
import com.product.product.entity.ProductEntity;
import com.product.product.mapper.ProductMapper;
import com.product.product.repository.ProductRepository;
import com.product.product.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        // Create test products
        ProductEntity product1 = new ProductEntity();
        product1.setId(1L);
        product1.setName("Test Product 1");
        product1.setDescription("Test Description 1");
        product1.setPrice(100.0);

        ProductEntity product2 = new ProductEntity();
        product2.setId(2L);
        product2.setName("Test Product 2");
        product2.setDescription("Test Description 2");
        product2.setPrice(200.0);

        // Create corresponding DTOs
        ProductDto dto1 = new ProductDto();
        dto1.setName("Test Product 1");
        dto1.setDescription("Test Description 1");
        dto1.setPrice(100.0);

        ProductDto dto2 = new ProductDto();
        dto2.setName("Test Product 2");
        dto2.setDescription("Test Description 2");
        dto2.setPrice(200.0);

        // Store test data as class fields for use in tests
        this.testProduct1 = product1;
        this.testProduct2 = product2;
        this.testDto1 = dto1;
        this.testDto2 = dto2;
    }

    private ProductEntity testProduct1;
    private ProductEntity testProduct2;
    private ProductDto testDto1;
    private ProductDto testDto2;

    @Test
    public void testCreateProduct() throws Exception {
        when(productMapper.toEntity(testDto1)).thenReturn(testProduct1);
        when(productRepository.save(testProduct1)).thenReturn(testProduct1);

        ProductEntity result = productService.createProduct(testDto1);

        assertEquals(testProduct1, result);
    }

    @Test
    public void testGetAllProducts() {
        List<ProductEntity> products = List.of(testProduct1, testProduct2);
        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.toDto(testProduct1)).thenReturn(testDto1);
        when(productMapper.toDto(testProduct2)).thenReturn(testDto2);

        List<ProductDto> result = productService.getAllProducts();

        assertEquals(2, result.size());
        assertEquals("Test Product 1", result.get(0).getName());
        assertEquals("Test Product 2", result.get(1).getName());
    }

    @Test
    public void testGetProductById() {
        when(productRepository.findById(testProduct1.getId())).thenReturn(Optional.of(testProduct1));
        when(productMapper.toDto(testProduct1)).thenReturn(testDto1);

        ProductDto result = productService.getProductById(testProduct1.getId());

        assertEquals(testDto1, result);
    }


    @Test
    public void testDeleteProduct() {
        when(productRepository.findById(testProduct1.getId())).thenReturn(Optional.of(testProduct1));
        productService.deleteProduct(testProduct1.getId());

        verify(productRepository).delete(testProduct1);
    }
}
