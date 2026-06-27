package com.tarak.ecommerce.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payments")
public class Payment {

    @Id
    private String id;

    private String paymentId;

    private String orderId;

    private String userId;

    private BigDecimal amount;

    private String paymentMode;

    private PaymentStatus status;

    private LocalDateTime createdAt;
}