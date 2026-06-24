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
}