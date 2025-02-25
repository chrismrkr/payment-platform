package com.example.my_payplatform.payment.service.port;

import com.example.my_payplatform.payment.domain.PaymentEvent;
import com.example.my_payplatform.payment.domain.PaymentOrder;

import java.util.List;

public interface PaymentOrderRepository {
    List<PaymentOrder> findByCheckoutId(String checkoutId);
    PaymentOrder findByPaymentOrderId(String paymentOrderId);
    PaymentOrder save(PaymentOrder paymentOrder);
    PaymentOrder persist(PaymentOrder paymentOrder);
}
