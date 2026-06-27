package com.tarak.ecommerce.controller.cart;

import com.tarak.ecommerce.dto.request.*;
import com.tarak.ecommerce.dto.response.CartResponse;
import com.tarak.ecommerce.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(
            Authentication authentication,
            @RequestBody CartRequest request) {

        return ResponseEntity.ok(
                cartService.addToCart(
                        authentication.getName(),
                        request));
    }

    @PutMapping("/update")
    public ResponseEntity<CartResponse> updateCart(
            Authentication authentication,
            @RequestBody CartUpdateRequest request) {

        return ResponseEntity.ok(
                cartService.updateCart(
                        authentication.getName(),
                        request));
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(
            Authentication authentication) {

        return ResponseEntity.ok(
                cartService.getCart(
                        authentication.getName()));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeFromCart(
            Authentication authentication,
            @PathVariable String productId) {

        cartService.removeFromCart(
                authentication.getName(),
                productId);

        return ResponseEntity.noContent().build();
    }
}