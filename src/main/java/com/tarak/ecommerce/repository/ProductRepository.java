package com.tarak.ecommerce.repository;

import com.tarak.ecommerce.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findByProductId(String productId);

    List<Product> findByProductNameContainingIgnoreCase(
            String keyword);
}