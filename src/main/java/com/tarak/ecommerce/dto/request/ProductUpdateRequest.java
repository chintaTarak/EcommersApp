package com.tarak.ecommerce.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {

    private String productName;

    private String description;

    private String category;

    private String brand;

    private BigDecimal price;

    private Integer quantity;

    private String imageUrl;
}