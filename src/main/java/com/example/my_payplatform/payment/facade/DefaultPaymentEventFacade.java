package com.example.my_payplatform.payment.facade;

import com.example.my_payplatform.payment.controller.dto.PaymentEventReqDto;
import com.example.my_payplatform.payment.controller.port.PaymentFacade;
import com.example.my_payplatform.payment.domain.PaymentEvent;
import com.example.my_payplatform.payment.facade.port.PaymentEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultPaymentEventFacade implements PaymentFacade {
    private final PaymentEventService paymentEventService;

    @Override
    public String startPayment(PaymentEventReqDto paymentEventReqDto) {
        PaymentEvent paymentEvent = paymentEventService.readyPayment(paymentEventReqDto);
        return paymentEventService.executePayment(paymentEventReqDto);
    }
}
