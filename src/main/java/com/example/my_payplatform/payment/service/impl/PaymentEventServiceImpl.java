package com.example.my_payplatform.payment.service.impl;

import com.example.my_payplatform.payment.service.port.PaymentEventRepository;
import com.example.my_payplatform.payment.service.port.PaymentOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventServiceImpl {
    private final PaymentOrderRepository paymentOrderRepository;
    private final PaymentEventRepository paymentEventRepository;



    private static class PaymentOrderExecutor {

    }
}
