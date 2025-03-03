package com.example.my_payplatform.payment.facade;

import com.example.my_payplatform.payment.controller.dto.PaymentEventReqDto;
import com.example.my_payplatform.payment.controller.dto.PaymentEventResultResDto;
import com.example.my_payplatform.payment.controller.dto.PaymentEventWebhookReqDto;
import com.example.my_payplatform.payment.controller.port.PaymentFacade;
import com.example.my_payplatform.payment.domain.PaymentEvent;
import com.example.my_payplatform.payment.domain.status.PaymentEventType;
import com.example.my_payplatform.payment.domain.status.PaymentResultType;
import com.example.my_payplatform.payment.facade.port.PaymentEventService;
import com.example.my_payplatform.payment.utils.UniqueIDGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultPaymentEventFacade implements PaymentFacade {
    private final PaymentEventService paymentEventService;
    private final Environment environment;

    @Override
    public String startPayment(PaymentEventReqDto paymentEventReqDto) {
        // TODO. 해당 메소드가 순차 처리되어 중복결제가 발생하지 않도록 해야 함
        paymentEventReqDto.setCheckoutId(UniqueIDGenerator.getId());

        PaymentEvent paymentEvent = paymentEventService.readyPayment(paymentEventReqDto);
        return paymentEventService.executePayment(paymentEventReqDto);
    }

    @Override
    public void handleWebhookEvent(PaymentEventWebhookReqDto reqDto) {
        paymentEventService.handleWebhook(reqDto);
    }

    @Override
    public PaymentEventResultResDto findPaymentResult(String paymentToken) {
        PaymentResultType resultType = paymentEventService.findPaymentResult(paymentToken);
        String url = null;
        if(resultType.equals(PaymentResultType.COMPLETED)) {
            url = environment.getProperty("payment-success.url");
        } else {
            url = environment.getProperty("payment-failure.url");
        }
        return PaymentEventResultResDto.builder()
                .resultType(resultType)
                .paymentResultUrl(url)
                .build();
    }
}
