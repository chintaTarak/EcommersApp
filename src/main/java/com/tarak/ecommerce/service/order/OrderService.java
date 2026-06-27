package com.tarak.ecommerce.service.order;

import com.tarak.ecommerce.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(String email);

    List<OrderResponse> orderHistory(String email);

    OrderResponse getOrder(String orderId);

    void cancelOrder(String orderId);
}