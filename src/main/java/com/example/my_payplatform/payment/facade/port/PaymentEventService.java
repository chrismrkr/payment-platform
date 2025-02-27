package com.example.my_payplatform.payment.facade.port;

import com.example.my_payplatform.payment.controller.dto.PaymentEventReqDto;
import com.example.my_payplatform.payment.controller.dto.PaymentEventWebhookReqDto;
import com.example.my_payplatform.payment.domain.PaymentEvent;

public interface PaymentEventService {
    PaymentEvent readyPaymentEvent(PaymentEventReqDto paymentEventReqDto);
    void readyPaymentOrder(PaymentEvent paymentEvent, PaymentEventReqDto paymentEventReqDto);
    String executePayment(PaymentEventReqDto paymentEventReqDto);
    void handleWebhook(PaymentEventWebhookReqDto webhookEvent);
}
