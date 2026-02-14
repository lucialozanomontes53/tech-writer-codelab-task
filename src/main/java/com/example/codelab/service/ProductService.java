package com.example.codelab.service;

import com.example.codelab.model.Product;
import com.example.codelab.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Product operations.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieves all products.
     *
     * @return list of all products
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Finds a product by its ID.
     *
     * @param id the product ID
     * @return an Optional containing the product if found
     */
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

}
