package com.tarak.ecommerce.service.cart.impl;

import com.tarak.ecommerce.dto.request.*;
import com.tarak.ecommerce.dto.response.*;
import com.tarak.ecommerce.entity.*;
import com.tarak.ecommerce.exception.ResourceNotFoundException;
import com.tarak.ecommerce.repository.*;
import com.tarak.ecommerce.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public CartResponse addToCart(
            String email,
            CartRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        Product product = productRepository
                .findByProductId(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"));

        Cart cart = cartRepository
                .findByUserId(user.getUserId())
                .orElse(
                        Cart.builder()
                                .userId(user.getUserId())
                                .items(new ArrayList<>())
                                .totalAmount(BigDecimal.ZERO)
                                .build()
                );

        CartItem item = CartItem.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .quantity(request.getQuantity())
                .price(product.getPrice())
                .totalPrice(
                        product.getPrice()
                                .multiply(
                                        BigDecimal.valueOf(
                                                request.getQuantity())))
                .build();

        cart.getItems().add(item);

        BigDecimal total =
                cart.getItems()
                        .stream()
                        .map(CartItem::getTotalPrice)
                        .reduce(BigDecimal.ZERO,
                                BigDecimal::add);

        cart.setTotalAmount(total);
        cart.setUpdatedAt(LocalDateTime.now());

        cartRepository.save(cart);

        return mapToResponse(cart);
    }

    @Override
    public CartResponse updateCart(
            String email,
            CartUpdateRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        Cart cart = cartRepository
                .findByUserId(user.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found"));

        cart.getItems().forEach(item -> {

            if (item.getProductId()
                    .equals(request.getProductId())) {

                item.setQuantity(request.getQuantity());

                item.setTotalPrice(
                        item.getPrice()
                                .multiply(
                                        BigDecimal.valueOf(
                                                request.getQuantity())));
            }
        });

        cart.setTotalAmount(
                cart.getItems()
                        .stream()
                        .map(CartItem::getTotalPrice)
                        .reduce(BigDecimal.ZERO,
                                BigDecimal::add));

        cartRepository.save(cart);

        return mapToResponse(cart);
    }

    @Override
    public CartResponse getCart(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        Cart cart = cartRepository
                .findByUserId(user.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found"));

        return mapToResponse(cart);
    }

    @Override
    public void removeFromCart(
            String email,
            String productId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        Cart cart = cartRepository
                .findByUserId(user.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found"));

        cart.getItems()
                .removeIf(item ->
                        item.getProductId()
                                .equals(productId));

        cart.setTotalAmount(
                cart.getItems()
                        .stream()
                        .map(CartItem::getTotalPrice)
                        .reduce(BigDecimal.ZERO,
                                BigDecimal::add));

        cartRepository.save(cart);
    }

    private CartResponse mapToResponse(
            Cart cart) {

        List<CartItemResponse> items =
                cart.getItems()
                        .stream()
                        .map(item ->
                                CartItemResponse.builder()
                                        .productId(item.getProductId())
                                        .productName(item.getProductName())
                                        .quantity(item.getQuantity())
                                        .price(item.getPrice())
                                        .totalPrice(item.getTotalPrice())
                                        .build())
                        .toList();

        return CartResponse.builder()
                .userId(cart.getUserId())
                .items(items)
                .totalAmount(cart.getTotalAmount())
                .build();
    }
}