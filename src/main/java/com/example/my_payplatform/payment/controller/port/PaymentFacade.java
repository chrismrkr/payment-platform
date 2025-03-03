package com.example.my_payplatform.payment.controller.port;

import com.example.my_payplatform.payment.controller.dto.PaymentEventReqDto;
import com.example.my_payplatform.payment.controller.dto.PaymentEventResultResDto;
import com.example.my_payplatform.payment.controller.dto.PaymentEventWebhookReqDto;

public interface PaymentFacade {
    String startPayment(PaymentEventReqDto paymentEventReqDto);
    void handleWebhookEvent(PaymentEventWebhookReqDto reqDto);
    PaymentEventResultResDto findPaymentResult(String paymentToken);
}
