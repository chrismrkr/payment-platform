package com.example.my_payplatform.payment.facade.port;

import com.example.my_payplatform.payment.controller.dto.PaymentEventReqDto;

public interface PaymentEventService {
    void readyPayment(PaymentEventReqDto paymentEventReqDto);
    String executePayment(PaymentEventReqDto paymentEventReqDto);
}
