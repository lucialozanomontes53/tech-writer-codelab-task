# Codelab: Implementing a Product Creation Endpoint

In this tutorial, you will extend a Spring Boot REST service by adding the ability to create new products. You will learn how to use Data Transfer Objects (DTOs) and implement a POST endpoint following modern best practices.

---

## Overview
Currently, the application only allows users to list products. By the end of this guide, you will have implemented a secure and scalable way to add new products to the system.

**Key concepts covered:**
* Creating Data Transfer Objects (DTOs) for input validation.
* Implementing service-layer logic.
* Exposing REST endpoints with proper HTTP status codes.

---

## Step 1: Create the Request DTO

To decouple our internal database structure from the API and prevent overposting, we use a **Data Transfer Object (DTO)**.

1. Navigate to the package `com.example.codelab.dto`.
2. Create a new file named `CreateProductRequest.java`.
3. Add the following code:

```java
package com.example.codelab.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * DTO for creating a new product.
 * Using a Java Record for immutability and conciseness.
 */
public record CreateProductRequest(
    @NotBlank(message = "Product name is required")
    String name,

    @Positive(message = "Price must be greater than zero")
    BigDecimal price
) {}

```

## Step 2: Update the Service Layer

We need to add a method in the service layer to handle the transformation from the DTO to the Product entity and persist it.

1. Open `src/main/java/com/example/codelab/service/ProductService.java`.

2. Add the following createProduct method:

```java
/**
 * Maps the DTO to a Product entity and saves it.
 */
public Product createProduct(CreateProductRequest request) {
    Product product = Product.builder()
        .name(request.getName())
        .description(request.getDescription())
        .price(request.getPrice())
        .category(request.getCategory())
        .stock(request.getStock() != null ? request.getStock() : 0)
        .build();

    return productRepository.save(product);
}
```

## Step 3: Expose the POST Endpoint
Finally, we will update the `ProductController` to expose the new functionality. We will use Constructor Injection and ensure we return the correct HTTP status code for resource creation.

Open `src/main/java/com/example/codelab/controller/ProductController.java`.

Implement the `@PostMapping` method as shown below:

```java
import com.example.codelab.dto.CreateProductRequest;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
```
## Verification
To verify your implementation, run the application using `./mvnw spring-boot:run` and send a POST request:

#### Request
```java
curl -X POST http://localhost:8080/products \
     -H "Content-Type: application/json" \
     -d '{"name": "New Smartphone", "price": 799.99}'
```

#### Expected response
**Status:** `201 Created`

**Body:** The JSON representation of the product including its new `id`.

## Summary
Congratulations! You have successfully implemented a creation endpoint using modern Spring Boot patterns, including Records for DTOs and Constructor Injection.
