package com.tarak.ecommerce.dto.response;

import com.tarak.ecommerce.entity.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {

    private String paymentId;

    private String orderId;

    private BigDecimal amount;

    private String paymentMode;

    private PaymentStatus status;

    private LocalDateTime createdAt;
}