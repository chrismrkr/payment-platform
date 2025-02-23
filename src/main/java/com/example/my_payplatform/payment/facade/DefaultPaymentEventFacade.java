package com.example.my_payplatform.payment.facade;

import com.example.my_payplatform.payment.facade.port.PaymentEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultPaymentEventFacade {
    private final PaymentEventService paymentEventService;


}
