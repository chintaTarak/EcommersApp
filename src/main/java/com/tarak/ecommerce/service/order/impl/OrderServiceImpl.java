package com.tarak.ecommerce.service.order.impl;

import com.tarak.ecommerce.dto.response.*;
import com.tarak.ecommerce.entity.*;
import com.tarak.ecommerce.exception.ResourceNotFoundException;
import com.tarak.ecommerce.repository.*;
import com.tarak.ecommerce.service.order.OrderService;
import com.tarak.ecommerce.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse placeOrder(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart is empty"));

        List<OrderItem> items = cart.getItems()
                .stream()
                .map(item -> OrderItem.builder()
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .totalPrice(item.getTotalPrice())
                        .build())
                .toList();

        Order order = Order.builder()
                .orderId(IdGenerator.generateOrderId())
                .userId(user.getUserId())
                .items(items)
                .totalAmount(cart.getTotalAmount())
                .status(OrderStatus.PLACED)
                .orderedAt(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        cartRepository.delete(cart);

        return mapToResponse(order);
    }

    @Override
    public List<OrderResponse> orderHistory(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return orderRepository.findByUserId(user.getUserId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrder(String orderId) {

        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        return mapToResponse(order);
    }

    @Override
    public void cancelOrder(String orderId) {

        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
    }

    private OrderResponse mapToResponse(Order order) {

        List<OrderItemResponse> items = order.getItems()
                .stream()
                .map(item -> OrderItemResponse.builder()
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .totalPrice(item.getTotalPrice())
                        .build())
                .toList();

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .items(items)
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .orderedAt(order.getOrderedAt())
                .build();
    }
}