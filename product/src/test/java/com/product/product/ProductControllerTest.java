package com.product.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.product.product.controller.ProductController;
import com.product.product.dto.ProductDto;
import com.product.product.entity.ProductEntity;
import com.product.product.service.ProductService;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ProductDto dto1;
    private ProductDto dto2;

    @BeforeEach
    void setUp() throws Exception {
        // Create test DTOs
        dto1 = new ProductDto();
        dto1.setName("Test Product 1");
        dto1.setDescription("Test Description 1");
        dto1.setPrice(100.0);

        dto2 = new ProductDto();
        dto2.setName("Test Product 2");
        dto2.setDescription("Test Description 2");
        dto2.setPrice(200.0);

        // Set up mock behavior
        when(productService.getAllProducts()).thenReturn(Arrays.asList(dto1, dto2));
        when(productService.getProductById(1L)).thenReturn(dto1);
        
        // Mock createProduct behavior
        ProductEntity newProduct = new ProductEntity();
        newProduct.setId(3L);
        newProduct.setName("Test Product 3");
        newProduct.setDescription("Test Description 3");
        newProduct.setPrice(300.0);
        
        ProductDto newProductDto = new ProductDto();
        newProductDto.setName("Test Product 3");
        newProductDto.setDescription("Test Description 3");
        newProductDto.setPrice(300.0);
        
        when(productService.createProduct(any(ProductDto.class))).thenReturn(newProduct);
        when(productService.getProductById(3L)).thenReturn(newProductDto);
    }

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].name").value("Test Product 1"))
            .andExpect(jsonPath("$[1].name").value("Test Product 2"));
    }
    
    @Test
    public void testGetProductById() throws Exception {
        mockMvc.perform(get("/products/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Test Product 1"))
            .andExpect(jsonPath("$.description").value("Test Description 1"))
            .andExpect(jsonPath("$.price").value(100.0))
            .andExpect(jsonPath("$.quantity").value(10));
    }


    @Test
    public void testCreateProduct() throws Exception {
        String response = mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"Test Product 3\", \"description\": \"Test Description 3\", \"price\": 300.0, \"quantity\": 30}"))
            .andExpect(status().isCreated())
            .andDo(print())
            .andReturn()
            .getResponse()
            .getContentAsString();
            
        System.out.println("Response: " + response);
            
        mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"Test Product 3\", \"description\": \"Test Description 3\", \"price\": 300.0, \"quantity\": 30}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Test Product 3"))
            .andExpect(jsonPath("$.description").value("Test Description 3"))
            .andExpect(jsonPath("$.price").value(300.0))
            .andExpect(jsonPath("$.quantity").value(30));
    }
}
