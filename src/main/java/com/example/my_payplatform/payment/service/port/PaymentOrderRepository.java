package com.example.my_payplatform.payment.service.port;

import com.example.my_payplatform.payment.domain.PaymentOrder;

public interface PaymentOrderRepository {
    PaymentOrder findByPaymentOrderId(String paymentOrderId);
    PaymentOrder save(PaymentOrder paymentOrder);
}
