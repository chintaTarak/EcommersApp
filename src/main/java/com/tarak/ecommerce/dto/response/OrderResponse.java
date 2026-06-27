package com.tarak.ecommerce.dto.response;

import com.tarak.ecommerce.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {

    private String orderId;

    private List<OrderItemResponse> items;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private LocalDateTime orderedAt;
}