package com.example.my_payplatform.payment.medium.service;

import com.example.my_payplatform.payment.controller.dto.PaymentEventReqDto;
import com.example.my_payplatform.payment.controller.dto.PaymentEventWebhookReqDto;
import com.example.my_payplatform.payment.domain.PaymentEvent;
import com.example.my_payplatform.payment.domain.PaymentOrder;
import com.example.my_payplatform.payment.domain.status.PaymentEventType;
import com.example.my_payplatform.payment.domain.status.PaymentOrderStatus;
import com.example.my_payplatform.payment.domain.status.PaymentResultType;
import com.example.my_payplatform.payment.infrastructure.PaymentEventJpaRepository;
import com.example.my_payplatform.payment.infrastructure.PaymentOrderJpaRepository;
import com.example.my_payplatform.payment.infrastructure.entity.PaymentEventEntity;
import com.example.my_payplatform.payment.service.impl.DefaultPaymentEventService;
import com.example.my_payplatform.payment.service.port.PaymentEventRepository;
import com.example.my_payplatform.payment.service.port.PaymentOrderRepository;
import com.example.my_payplatform.payment.utils.UniqueIDGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;

import java.util.*;

@SpringBootTest
@Slf4j
public class DefaultPaymentEventServiceTest {
    @Autowired
    DefaultPaymentEventService defaultPaymentEventService;
    @Autowired
    PaymentEventRepository paymentEventRepository;
    @Autowired
    PaymentOrderRepository paymentOrderRepository;
    @Autowired
    PaymentEventJpaRepository paymentEventJpaRepository;
    @Autowired
    PaymentOrderJpaRepository paymentOrderJpaRepository;
    @Autowired
    EntityManager em;

    @AfterEach
    void DB_초기화() {
        paymentEventJpaRepository.deleteAll();
    }

    @Test
    @Rollback
    void 결제이벤트_및_주문_준비() {
        // given
        PaymentEventReqDto.PaymentOrderInfo farmerOrderInfo = PaymentEventReqDto.PaymentOrderInfo.builder()
                .sellerInfo("farmer")
                .amount("10000")
                .currency("won")
                .build();
        PaymentEventReqDto.PaymentOrderInfo butcherOrderInfo = PaymentEventReqDto.PaymentOrderInfo.builder()
                .sellerInfo("butcher")
                .amount("30000")
                .currency("won")
                .build();
        List<PaymentEventReqDto.PaymentOrderInfo> paymentOrderInfoList = new ArrayList<>();
        paymentOrderInfoList.add(farmerOrderInfo);
        paymentOrderInfoList.add(butcherOrderInfo);
        PaymentEventReqDto paymentEventReqDto = PaymentEventReqDto.builder()
                .buyerInfo("kim")
                .buyerAccount("123Account321")
                .creditCardInfo("PopularPSP-code99")
                .paymentOrderInfos(paymentOrderInfoList)
                .build();
        // when
        paymentEventReqDto.setCheckoutId("youShouldSetIdempotencyKeyByUUIDGenerator");

        PaymentEvent paymentEvent = defaultPaymentEventService.readyPayment(paymentEventReqDto);

        // then
        Assertions.assertEquals("youShouldSetIdempotencyKeyByUUIDGenerator",
                paymentEventRepository.findByCheckoutId("youShouldSetIdempotencyKeyByUUIDGenerator")
                        .getCheckoutId());
        Assertions.assertEquals(2, paymentOrderRepository.findByCheckoutId("youShouldSetIdempotencyKeyByUUIDGenerator").size());
    }

    @Test
    @Rollback
    void 결제_이벤트_및_주문_시작() {
        // given
        String checkoutId = UniqueIDGenerator.getId();
        PaymentEventReqDto.PaymentOrderInfo farmerOrderInfo = PaymentEventReqDto.PaymentOrderInfo.builder()
                .sellerInfo("farmer")
                .amount("10000")
                .currency("won")
                .build();
        PaymentEventReqDto.PaymentOrderInfo butcherOrderInfo = PaymentEventReqDto.PaymentOrderInfo.builder()
                .sellerInfo("butcher")
                .amount("30000")
                .currency("won")
                .build();
        List<PaymentEventReqDto.PaymentOrderInfo> paymentOrderInfoList = new ArrayList<>();
        paymentOrderInfoList.add(farmerOrderInfo);
        paymentOrderInfoList.add(butcherOrderInfo);
        PaymentEventReqDto paymentEventReqDto = PaymentEventReqDto.builder()
                .buyerInfo("kim")
                .buyerAccount("123Account321")
                .creditCardInfo("PopularPSP-code99")
                .paymentOrderInfos(paymentOrderInfoList)
                .build();
        paymentEventReqDto.setCheckoutId(checkoutId);

        PaymentEvent paymentEvent = defaultPaymentEventService.readyPayment(paymentEventReqDto);

        // when
        String s = defaultPaymentEventService.executePayment(paymentEventReqDto);

        // then
        Assertions.assertTrue(paymentEventRepository.findByCheckoutId(checkoutId).getPaymentToken().length() > 0);
        Assertions.assertTrue(s.length() > 0);
        for(PaymentOrder paymentOrder : paymentOrderRepository.findByCheckoutId(checkoutId)) {
            Assertions.assertEquals(PaymentOrderStatus.EXECUTING, paymentOrder.getPaymentOrderStatus());
        }
    }

    @Test
    void 결제_이벤트_및_주문_완료_웹훅_처리() {
        // given
        String checkoutId = "1";
        String paymentToken = UniqueIDGenerator.getId();
        PaymentEvent paymentEvent = PaymentEvent.builder()
                .checkoutId(checkoutId)
                .buyerInfo("kim")
                .creditCardInfo("abcCard123")
                .isPaymentDone(false)
                .paymentToken(paymentToken)
                .build();
        String paymentOrderId1 = "123";
        PaymentOrder paymentOrder1 = PaymentOrder.builder()
                .paymentOrderId(paymentOrderId1)
                .sellerInfo("lee")
                .amount("1000")
                .currency("won")
                .paymentOrderStatus(PaymentOrderStatus.EXECUTING)
                .ledgerUpdated(false)
                .walletUpdated(false)
                .build();
        String paymentOrderId2 = "456";
        PaymentOrder paymentOrder2 = PaymentOrder.builder()
                .paymentOrderId(paymentOrderId2)
                .sellerInfo("park")
                .amount("3000")
                .currency("won")
                .paymentOrderStatus(PaymentOrderStatus.EXECUTING)
                .ledgerUpdated(false)
                .walletUpdated(false)
                .build();
        paymentEvent.getPaymentOrders().add(paymentOrder1);
        paymentEvent.getPaymentOrders().add(paymentOrder2);
        paymentOrder1.setPaymentEvent(paymentEvent);
        paymentOrder2.setPaymentEvent(paymentEvent);
        PaymentEvent paymentEvent1 = paymentEventRepository.persistWithPaymentOrder(paymentEvent);

        // when
        PaymentEventWebhookReqDto reqDto = PaymentEventWebhookReqDto.builder()
                .paymentToken(paymentToken)
                .eventType(PaymentEventType.PAYMENT_STATUS_CHANGED.toString())
                .status(PaymentOrderStatus.SUCCESS.toString())
                .build();

        defaultPaymentEventService.handleWebhook(reqDto);

        // then
        PaymentEvent byPaymentToken = paymentEventRepository.findByPaymentToken(paymentToken);
        List<PaymentOrder> byCheckoutId = paymentOrderRepository.findByCheckoutId(byPaymentToken.getCheckoutId());
        Assertions.assertEquals(paymentToken, byPaymentToken.getPaymentToken());
        Assertions.assertEquals(2, byCheckoutId.size());

    }

    @Test
    void 결제_결과_조회() {
        // given
        String checkoutId = "1";
        String paymentToken = UniqueIDGenerator.getId();
        PaymentEvent paymentEvent = PaymentEvent.builder()
                .checkoutId(checkoutId)
                .buyerInfo("kim")
                .creditCardInfo("abcCard123")
                .isPaymentDone(false)
                .paymentToken(paymentToken)
                .build();
        String paymentOrderId1 = "123";
        PaymentOrder paymentOrder1 = PaymentOrder.builder()
                .paymentOrderId(paymentOrderId1)
                .sellerInfo("lee")
                .amount("1000")
                .currency("won")
                .paymentOrderStatus(PaymentOrderStatus.EXECUTING)
                .ledgerUpdated(false)
                .walletUpdated(false)
                .build();
        String paymentOrderId2 = "456";
        PaymentOrder paymentOrder2 = PaymentOrder.builder()
                .paymentOrderId(paymentOrderId2)
                .sellerInfo("park")
                .amount("3000")
                .currency("won")
                .paymentOrderStatus(PaymentOrderStatus.EXECUTING)
                .ledgerUpdated(false)
                .walletUpdated(false)
                .build();
        paymentEvent.getPaymentOrders().add(paymentOrder1);
        paymentEvent.getPaymentOrders().add(paymentOrder2);
        paymentOrder1.setPaymentEvent(paymentEvent);
        paymentOrder2.setPaymentEvent(paymentEvent);
        PaymentEvent paymentEvent1 = paymentEventRepository.persistWithPaymentOrder(paymentEvent);
        PaymentEventWebhookReqDto reqDto = PaymentEventWebhookReqDto.builder()
                .paymentToken(paymentToken)
                .eventType(PaymentEventType.PAYMENT_STATUS_CHANGED.toString())
                .status(PaymentOrderStatus.SUCCESS.toString())
                .build();
        defaultPaymentEventService.handleWebhook(reqDto);

        // when
        PaymentResultType paymentResult = defaultPaymentEventService.findPaymentResult(paymentToken);

        // then
        Assertions.assertEquals(PaymentResultType.COMPLETED, paymentResult);
    }

}
