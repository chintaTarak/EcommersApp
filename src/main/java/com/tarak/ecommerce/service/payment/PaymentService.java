package com.tarak.ecommerce.service.payment;

import com.tarak.ecommerce.dto.request.*;
import com.tarak.ecommerce.dto.response.PaymentResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse initiatePayment(
            String email,
            PaymentInitiateRequest request);

    PaymentResponse verifyPayment(
            PaymentVerifyRequest request);

    PaymentResponse refundPayment(
            RefundRequest request);

    PaymentResponse getPayment(String paymentId);

    List<PaymentResponse> paymentHistory(String email);
}