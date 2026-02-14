package com.example.codelab.controller;

import com.example.codelab.model.Product;
import com.example.codelab.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .id("test-id-123")
                .name("Test Product")
                .description("A test product")
                .price(29.99)
                .category("Test")
                .stock(10)
                .build();
    }

    @Test
    @DisplayName("GET /products should return all products")
    void getAllProducts_ShouldReturnProductList() throws Exception {
        when(productService.getAllProducts()).thenReturn(Arrays.asList(testProduct));

        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].price").value(29.99));
    }

    @Test
    @DisplayName("GET /products/{id} should return product when found")
    void getProductById_WhenFound_ShouldReturnProduct() throws Exception {
        when(productService.getProductById("test-id-123")).thenReturn(Optional.of(testProduct));

        mockMvc.perform(get("/products/test-id-123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.id").value("test-id-123"));
    }

    @Test
    @DisplayName("GET /products/{id} should return 404 when not found")
    void getProductById_WhenNotFound_ShouldReturn404() throws Exception {
        when(productService.getProductById("non-existent")).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/non-existent")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // TODO: Add tests for POST endpoint

}
