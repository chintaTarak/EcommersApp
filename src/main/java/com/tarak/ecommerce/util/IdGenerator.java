package com.tarak.ecommerce.util;

import java.util.UUID;

public class IdGenerator {

    public static String generateUserId() {

        return "USR-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
    }
    public static String generateProductId() {

        return "PRD-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
    }

    public static String generateOrderId() {

        return "ORD-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0,8)
                        .toUpperCase();
    }

    public static String generatePaymentId() {

        return "PAY-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0,8)
                        .toUpperCase();
    }
}