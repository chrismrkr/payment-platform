package com.example.my_payplatform.payment.controller.port;

import com.example.my_payplatform.payment.controller.dto.PaymentEventReqDto;

public interface PaymentFacade {
    String startPayment(PaymentEventReqDto paymentEventReqDto);
}
