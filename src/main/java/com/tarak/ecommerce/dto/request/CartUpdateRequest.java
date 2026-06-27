package com.tarak.ecommerce.dto.request;

import lombok.Data;

@Data
public class CartUpdateRequest {

    private String productId;

    private Integer quantity;
}