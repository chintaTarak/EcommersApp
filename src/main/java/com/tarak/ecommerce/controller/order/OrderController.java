package com.tarak.ecommerce.controller.order;

import com.tarak.ecommerce.dto.request.CancelOrderRequest;
import com.tarak.ecommerce.dto.response.OrderResponse;
import com.tarak.ecommerce.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            Authentication authentication) {

        return ResponseEntity.ok(
                orderService.placeOrder(authentication.getName()));
    }

    @GetMapping("/history")
    public ResponseEntity<List<OrderResponse>> history(
            Authentication authentication) {

        return ResponseEntity.ok(
                orderService.orderHistory(authentication.getName()));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
            @PathVariable String orderId) {

        return ResponseEntity.ok(
                orderService.getOrder(orderId));
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelOrder(
            @RequestBody CancelOrderRequest request) {

        orderService.cancelOrder(request.getOrderId());

        return ResponseEntity.ok("Order cancelled successfully");
    }
}