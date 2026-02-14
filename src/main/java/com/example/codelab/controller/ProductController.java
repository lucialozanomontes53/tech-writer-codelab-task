package com.example.codelab.controller;

import com.example.codelab.dto.CreateProductRequest;
import com.example.codelab.model.Product;
import com.example.codelab.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for Product operations.
 */
@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Product management endpoints")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieves all products.
     *
     * @return list of all products
     */
    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves a list of all available products")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the product ID
     * @return the product if found, or 404 if not found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a specific product by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> getProductById(
            @Parameter(description = "Product ID") @PathVariable String id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new product.
     *
     * @param request the product creation request
     * @return the created product with HTTP 201 status
     */
    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product in the catalog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<Product> createProduct(
            @Parameter(description = "Product creation data") @Valid @RequestBody CreateProductRequest request) {
        Product createdProduct = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

}
