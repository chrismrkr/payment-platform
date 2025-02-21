package com.example.my_payplatform.payment.service.impl;

import com.example.my_payplatform.payment.controller.dto.PaymentEventReqDto;
import com.example.my_payplatform.payment.domain.PaymentEvent;
import com.example.my_payplatform.payment.service.port.PaymentEventRepository;
import com.example.my_payplatform.payment.service.port.PaymentOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventServiceImpl {
    private final PaymentOrderRepository paymentOrderRepository;
    private final PaymentEventRepository paymentEventRepository;


    public void doPaymentEvent(PaymentEventReqDto paymentEventReqDto) {
        PaymentEvent paymentEvent = PaymentEvent.builder()
                .checkoutId()
                .buyerInfo(paymentEventReqDto.getBuyerInfo())
                .creditCardInfo(paymentEventReqDto.getCreditCardOInfo())
                .isPaymentDone(false)
                .build();

    }

    private class PaymentOrderExecutor {
        public String startPayment() {

            return "redirect PSP Payment Page URL";
        }
    }
}
