package com.tarak.ecommerce.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponse {

    private String productId;

    private String productName;

    private String description;

    private String category;

    private String brand;

    private BigDecimal price;

    private Integer quantity;

    private String imageUrl;
}