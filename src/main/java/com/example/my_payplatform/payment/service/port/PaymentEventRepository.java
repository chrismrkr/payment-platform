package com.example.my_payplatform.payment.service.port;

import com.example.my_payplatform.payment.domain.PaymentEvent;

public interface PaymentEventRepository {
    PaymentEvent findByCheckoutId(String checkoutId);
    PaymentEvent findByPaymentTokenWithPaymentOrder(String paymentToken);
    PaymentEvent findByPaymentToken(String paymentToken);
    PaymentEvent save(PaymentEvent paymentEvent);
    PaymentEvent persistWithPaymentOrder(PaymentEvent paymentEvent);
}
