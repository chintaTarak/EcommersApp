package com.tarak.ecommerce.controller.payment;

import com.tarak.ecommerce.dto.request.*;
import com.tarak.ecommerce.dto.response.PaymentResponse;
import com.tarak.ecommerce.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<PaymentResponse> initiate(
            Authentication authentication,
            @RequestBody PaymentInitiateRequest request) {

        return ResponseEntity.ok(
                paymentService.initiatePayment(
                        authentication.getName(),
                        request));
    }

    @PostMapping("/verify")
    public ResponseEntity<PaymentResponse> verify(
            @RequestBody PaymentVerifyRequest request) {

        return ResponseEntity.ok(
                paymentService.verifyPayment(request));
    }

    @PostMapping("/refund")
    public ResponseEntity<PaymentResponse> refund(
            @RequestBody RefundRequest request) {

        return ResponseEntity.ok(
                paymentService.refundPayment(request));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(
            @PathVariable String paymentId) {

        return ResponseEntity.ok(
                paymentService.getPayment(paymentId));
    }

    @GetMapping("/history")
    public ResponseEntity<List<PaymentResponse>> history(
            Authentication authentication) {

        return ResponseEntity.ok(
                paymentService.paymentHistory(
                        authentication.getName()));
    }
}