package com.example.my_payplatform.payment.service.port;

import com.example.my_payplatform.payment.domain.PaymentEvent;

public interface PaymentEventRepository {
    PaymentEvent findByCheckoutId(String checkoutId);
    PaymentEvent save(PaymentEvent paymentEvent);
}
