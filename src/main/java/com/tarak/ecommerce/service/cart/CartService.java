package com.tarak.ecommerce.service.cart;

import com.tarak.ecommerce.dto.request.CartRequest;
import com.tarak.ecommerce.dto.request.CartUpdateRequest;
import com.tarak.ecommerce.dto.response.CartResponse;

public interface CartService {

    CartResponse addToCart(
            String email,
            CartRequest request);

    CartResponse updateCart(
            String email,
            CartUpdateRequest request);

    CartResponse getCart(String email);

    void removeFromCart(
            String email,
            String productId);
}