package com.tarak.ecommerce.service.product;

import com.tarak.ecommerce.dto.request.ProductCreateRequest;
import com.tarak.ecommerce.dto.response.ProductResponse;
import com.tarak.ecommerce.entity.Product;
import com.tarak.ecommerce.repository.ProductRepository;
import com.tarak.ecommerce.service.product.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateProductSuccessfully() {

        ProductCreateRequest request =
                new ProductCreateRequest();

        request.setProductName("Silver Coin");
        request.setDescription("999 Silver");
        request.setCategory("Silver");
        request.setBrand("NEK");
        request.setPrice(BigDecimal.valueOf(2000));
        request.setQuantity(10);
        request.setImageUrl("image.jpg");

        when(repository.save(any(Product.class)))
                .thenAnswer(i -> i.getArgument(0));

        ProductResponse response =
                service.createProduct(request);

        assertNotNull(response);
        assertEquals("Silver Coin",
                response.getProductName());
    }

    @Test
    void shouldGetAllProducts() {

        Product product = Product.builder()
                .productId("PRD-123")
                .productName("Silver Coin")
                .price(BigDecimal.valueOf(2000))
                .build();

        when(repository.findAll())
                .thenReturn(List.of(product));

        List<ProductResponse> responses =
                service.getAllProducts();

        assertEquals(1, responses.size());
    }

    @Test
    void shouldGetProductById() {

        Product product = Product.builder()
                .productId("PRD-123")
                .productName("Silver Coin")
                .price(BigDecimal.valueOf(2000))
                .build();

        when(repository.findByProductId("PRD-123"))
                .thenReturn(Optional.of(product));

        ProductResponse response =
                service.getProductById("PRD-123");

        assertNotNull(response);
        assertEquals("PRD-123",
                response.getProductId());
    }

    @Test
    void shouldDeleteProduct() {

        Product product = Product.builder()
                .productId("PRD-123")
                .build();

        when(repository.findByProductId("PRD-123"))
                .thenReturn(Optional.of(product));

        service.deleteProduct("PRD-123");

        verify(repository, times(1))
                .delete(product);
    }
}