package com.tarak.ecommerce.dto.request;

import lombok.Data;

@Data
public class PaymentInitiateRequest {

    private String orderId;

    private String paymentMode;
}