package com.tarak.ecommerce.service.payment.impl;

import com.tarak.ecommerce.dto.request.*;
import com.tarak.ecommerce.dto.response.PaymentResponse;
import com.tarak.ecommerce.entity.*;
import com.tarak.ecommerce.exception.ResourceNotFoundException;
import com.tarak.ecommerce.repository.*;
import com.tarak.ecommerce.service.payment.PaymentService;
import com.tarak.ecommerce.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponse initiatePayment(
            String email,
            PaymentInitiateRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Order order = orderRepository
                .findByOrderId(request.getOrderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        Payment payment = Payment.builder()
                .paymentId(IdGenerator.generatePaymentId())
                .orderId(order.getOrderId())
                .userId(user.getUserId())
                .amount(order.getTotalAmount())
                .paymentMode(request.getPaymentMode())
                .status(PaymentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        return mapToResponse(payment);
    }

    @Override
    public PaymentResponse verifyPayment(
            PaymentVerifyRequest request) {

        Payment payment = paymentRepository
                .findByPaymentId(request.getPaymentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found"));

        payment.setStatus(PaymentStatus.SUCCESS);

        paymentRepository.save(payment);

        return mapToResponse(payment);
    }

    @Override
    public PaymentResponse refundPayment(
            RefundRequest request) {

        Payment payment = paymentRepository
                .findByPaymentId(request.getPaymentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found"));

        payment.setStatus(PaymentStatus.REFUNDED);

        paymentRepository.save(payment);

        return mapToResponse(payment);
    }

    @Override
    public PaymentResponse getPayment(String paymentId) {

        Payment payment = paymentRepository
                .findByPaymentId(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found"));

        return mapToResponse(payment);
    }

    @Override
    public List<PaymentResponse> paymentHistory(
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return paymentRepository
                .findByUserId(user.getUserId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private PaymentResponse mapToResponse(Payment payment) {

        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .orderId(payment.getOrderId())
                .amount(payment.getAmount())
                .paymentMode(payment.getPaymentMode())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}