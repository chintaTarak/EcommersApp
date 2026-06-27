package com.tarak.ecommerce.controller.product;

import com.tarak.ecommerce.dto.request.*;
import com.tarak.ecommerce.dto.response.ProductResponse;
import com.tarak.ecommerce.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductCreateRequest request) {

        return new ResponseEntity<>(
                productService.createProduct(request),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>>
    getAllProducts() {

        return ResponseEntity.ok(
                productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse>
    getProductById(
            @PathVariable String productId) {

        return ResponseEntity.ok(
                productService.getProductById(productId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>>
    searchProducts(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                productService.searchProducts(keyword));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse>
    updateProduct(
            @PathVariable String productId,
            @RequestBody ProductUpdateRequest request) {

        return ResponseEntity.ok(
                productService.updateProduct(
                        productId,
                        request));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable String productId) {

        productService.deleteProduct(productId);

        return ResponseEntity.noContent().build();
    }
}