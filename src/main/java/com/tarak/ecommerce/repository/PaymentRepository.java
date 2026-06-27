package com.tarak.ecommerce.repository;

import com.tarak.ecommerce.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository
        extends MongoRepository<Payment, String> {

    Optional<Payment> findByPaymentId(String paymentId);

    List<Payment> findByUserId(String userId);
}