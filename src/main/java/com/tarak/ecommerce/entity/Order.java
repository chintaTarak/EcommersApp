package com.tarak.ecommerce.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String orderId;

    private String userId;

    private List<OrderItem> items;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private LocalDateTime orderedAt;
}