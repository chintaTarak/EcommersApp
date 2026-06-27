package com.tarak.ecommerce.service.product;

import com.tarak.ecommerce.dto.request.ProductCreateRequest;
import com.tarak.ecommerce.dto.request.ProductUpdateRequest;
import com.tarak.ecommerce.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(
            ProductCreateRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(String productId);

    List<ProductResponse> searchProducts(String keyword);

    ProductResponse updateProduct(
            String productId,
            ProductUpdateRequest request);

    void deleteProduct(String productId);
}