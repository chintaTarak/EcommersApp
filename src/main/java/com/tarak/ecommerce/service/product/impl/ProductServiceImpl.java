package com.tarak.ecommerce.service.product.impl;

import com.tarak.ecommerce.dto.request.*;
import com.tarak.ecommerce.dto.response.ProductResponse;
import com.tarak.ecommerce.entity.Product;
import com.tarak.ecommerce.exception.ResourceNotFoundException;
import com.tarak.ecommerce.repository.ProductRepository;
import com.tarak.ecommerce.service.product.ProductService;
import com.tarak.ecommerce.util.IdGenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public ProductResponse createProduct(
            ProductCreateRequest request) {

        Product product = Product.builder()
                .productId(IdGenerator.generateProductId())
                .productName(request.getProductName())
                .description(request.getDescription())
                .category(request.getCategory())
                .brand(request.getBrand())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .imageUrl(request.getImageUrl())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        repository.save(product);

        return mapToResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(
            String productId) {

        Product product = repository
                .findByProductId(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"));

        return mapToResponse(product);
    }

    @Override
    public List<ProductResponse> searchProducts(
            String keyword) {

        return repository
                .findByProductNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductResponse updateProduct(
            String productId,
            ProductUpdateRequest request) {

        Product product = repository
                .findByProductId(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"));

        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImageUrl(request.getImageUrl());
        product.setUpdatedAt(LocalDateTime.now());

        repository.save(product);

        return mapToResponse(product);
    }

    @Override
    public void deleteProduct(String productId) {

        Product product = repository
                .findByProductId(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"));

        repository.delete(product);
    }

    private ProductResponse mapToResponse(
            Product product) {

        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .category(product.getCategory())
                .brand(product.getBrand())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .imageUrl(product.getImageUrl())
                .build();
    }
}