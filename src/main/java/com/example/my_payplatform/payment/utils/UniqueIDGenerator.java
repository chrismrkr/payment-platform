package com.example.my_payplatform.payment.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UniqueIDGenerator {
    public synchronized static String getId() {
        return UUID.randomUUID().toString();
    }
}
