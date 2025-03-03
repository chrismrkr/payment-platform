package com.example.my_payplatform.payment.service.impl;

import com.example.my_payplatform.payment.controller.dto.PaymentEventReqDto;
import com.example.my_payplatform.payment.controller.dto.PaymentEventResultResDto;
import com.example.my_payplatform.payment.controller.dto.PaymentEventWebhookReqDto;
import com.example.my_payplatform.payment.domain.PaymentEvent;
import com.example.my_payplatform.payment.domain.PaymentOrder;
import com.example.my_payplatform.payment.domain.status.PaymentEventType;
import com.example.my_payplatform.payment.domain.status.PaymentOrderStatus;
import com.example.my_payplatform.payment.domain.status.PaymentResultType;
import com.example.my_payplatform.payment.facade.port.PaymentEventService;
import com.example.my_payplatform.payment.service.executor.common.dto.PaymentExecuteResponseDto;
import com.example.my_payplatform.payment.service.port.PaymentEventRepository;
import com.example.my_payplatform.payment.service.port.PaymentOrderRepository;
import com.example.my_payplatform.payment.utils.UniqueIDGenerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultPaymentEventService implements PaymentEventService {
    private final PaymentEventRepository paymentEventRepository;
    private final PaymentOrderRepository paymentOrderRepository;
    private final ApplicationContext applicationContext;
    private final Environment env;

    @Override
    @Transactional
    public PaymentEvent readyPayment(PaymentEventReqDto paymentEventReqDto) {
        PaymentEvent paymentEvent = createPaymentEvent(paymentEventReqDto);
        paymentEventReqDto.getPaymentOrderInfos()
                .forEach(paymentOrderInfo -> {
                    paymentOrderInfo.setPaymentOrderId(UniqueIDGenerator.getId());
                    addPaymentOrder(paymentOrderInfo, paymentEvent);
                });
        paymentEventRepository.persistWithPaymentOrder(paymentEvent);
        return paymentEvent;
    }


    @Override
    @Transactional
    public String executePayment(PaymentEventReqDto paymentEventReqDto) {
        // TODO. 결제 수단에 맞는 PaymentExecutor 조회하도록 변경
        PaymentExecutor paymentExecutor = applicationContext.getBean(PaymentExecutor.class);

        // 결제 요청
        PaymentExecuteResponseDto paymentEnrollResult = paymentExecutor.enrollPayment(paymentEventReqDto);

        // 상태 변경(NOT_STARTING -> EXECUTING)
        String paymentEventToken = paymentEnrollResult.getPaymentEventToken();
        String redirectPageUrl = paymentEnrollResult.getRedirectPageUrl();

        // PaymentToken DB 저장
        String checkoutId = paymentEventReqDto.getCheckoutId();
        PaymentEvent paymentEvent = paymentEventRepository.findByCheckoutId(checkoutId);
        paymentEvent.setPaymentToken(paymentEventToken);
        paymentEventRepository.save(paymentEvent);

        //
        paymentEventReqDto.getPaymentOrderInfos().forEach(paymentOrderInfo -> {
            String paymentOrderId = paymentOrderInfo.getPaymentOrderId();
            PaymentOrder paymentOrder = paymentOrderRepository.findByPaymentOrderId(paymentOrderId);
            paymentOrder.changeStatus(PaymentOrderStatus.EXECUTING);
            paymentOrder.setPaymentEvent(paymentEvent);
            paymentOrderRepository.save(paymentOrder);
        });

        return redirectPageUrl +
                "?paymentToken=" +
                paymentEventToken;
    }

    @Override
    @Transactional
    public void handleWebhook(PaymentEventWebhookReqDto webhookEvent) {
        String eventType = webhookEvent.getEventType();
        String paymentToken = webhookEvent.getPaymentToken();
        PaymentOrderStatus paymentOrderStatus = PaymentOrderStatus.valueOf(webhookEvent.getStatus());
        if(eventType.equals(PaymentEventType.PAYMENT_STATUS_CHANGED.name())) {
            PaymentEvent paymentEvent = paymentEventRepository.findByPaymentTokenWithPaymentOrder(paymentToken);
            paymentEvent.donePayment();
            paymentEventRepository.save(paymentEvent);
            for(PaymentOrder paymentOrder : paymentEvent.getPaymentOrders()) {
                paymentOrder.changeStatus(paymentOrderStatus);
                paymentOrderRepository.save(paymentOrder);
            }
        } else {
            throw new IllegalArgumentException("Not Supported Event Type: " + eventType);
        }
    }

    @Override
    public PaymentResultType findPaymentResult(String paymentToken) {
        PaymentEvent paymentEvent = paymentEventRepository.findByPaymentTokenWithPaymentOrder(paymentToken);
        if(!paymentEvent.isPaymentDone()) {
            return PaymentResultType.EXECUTING;
        }
        List<PaymentOrder> paymentOrders = paymentEvent.getPaymentOrders();

        if(paymentOrders.stream().anyMatch(paymentOrder ->
                paymentOrder.getPaymentOrderStatus().equals(PaymentOrderStatus.EXECUTING))) {
            return PaymentResultType.EXECUTING;
        } else if(paymentOrders.stream().anyMatch(paymentOrder ->
                paymentOrder.getPaymentOrderStatus().equals(PaymentOrderStatus.FAILED))) {
            return PaymentResultType.FAILED;
        } else {
            return PaymentResultType.COMPLETED;
        }
    }

    private PaymentEvent createPaymentEvent(PaymentEventReqDto dto) {
        PaymentEvent paymentEvent = PaymentEvent.builder()
                .checkoutId(dto.getCheckoutId())
                .buyerInfo(dto.getBuyerInfo())
                .creditCardInfo(dto.getCreditCardInfo())
                .isPaymentDone(false)
                .build();
        return paymentEvent;
    }

    private void addPaymentOrder(PaymentEventReqDto.PaymentOrderInfo paymentOrderInfo, PaymentEvent paymentEvent) {
        PaymentOrder paymentOrder = PaymentOrder.builder()
                .paymentOrderId(paymentOrderInfo.getPaymentOrderId())
                .sellerInfo(paymentOrderInfo.getSellerInfo())
                .amount(paymentOrderInfo.getAmount())
                .currency(paymentOrderInfo.getCurrency())
                .paymentOrderStatus(PaymentOrderStatus.NOT_STARTED)
                .ledgerUpdated(false)
                .walletUpdated(false)
                .build();
        paymentEvent.getPaymentOrders().add(paymentOrder);
        paymentOrder.setPaymentEvent(paymentEvent);
    }

    public interface PaymentExecutor {
        PaymentExecuteResponseDto enrollPayment(PaymentEventReqDto reqDto);
    }
}
