package com.example.codelab.repository;

import com.example.codelab.model.Product;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory repository for Product entities.
 * In a real application, this would connect to a database.
 */
@Repository
public class ProductRepository {

    private final Map<String, Product> products = new ConcurrentHashMap<>();

    public ProductRepository() {
        // Initialize with some sample data
        Product sample1 = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Classic T-Shirt")
                .description("Cotton t-shirt in white")
                .price(19.99)
                .category("Tops")
                .stock(100)
                .build();

        Product sample2 = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Slim Fit Jeans")
                .description("Dark blue slim fit jeans")
                .price(49.99)
                .category("Bottoms")
                .stock(50)
                .build();

        products.put(sample1.getId(), sample1);
        products.put(sample2.getId(), sample2);
    }

    /**
     * Retrieves all products.
     *
     * @return list of all products
     */
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    /**
     * Finds a product by its ID.
     *
     * @param id the product ID
     * @return an Optional containing the product if found
     */
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(products.get(id));
    }

    /**
     * Saves a product to the repository.
     *
     * @param product the product to save
     * @return the saved product
     */
    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(UUID.randomUUID().toString());
        }
        products.put(product.getId(), product);
        return product;
    }

}
