package com.example.my_payplatform.payment.facade;

import com.example.my_payplatform.payment.controller.dto.PaymentEventReqDto;
import com.example.my_payplatform.payment.controller.port.PaymentFacade;
import com.example.my_payplatform.payment.domain.PaymentEvent;
import com.example.my_payplatform.payment.facade.port.PaymentEventService;
import com.example.my_payplatform.payment.utils.UniqueIDGenerator;
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
        // TODO. 해당 메소드가 순차 처리되어 중복결제가 발생하지 않도록 해야 함
        paymentEventReqDto.setCheckoutId(UniqueIDGenerator.getId());

        PaymentEvent paymentEvent = paymentEventService.readyPayment(paymentEventReqDto);
        return paymentEventService.executePayment(paymentEventReqDto);
    }
}
