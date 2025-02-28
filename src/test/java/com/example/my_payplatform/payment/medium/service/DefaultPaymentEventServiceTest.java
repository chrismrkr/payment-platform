package com.example.my_payplatform.payment.medium.service;

import com.example.my_payplatform.payment.controller.dto.PaymentEventReqDto;
import com.example.my_payplatform.payment.domain.PaymentEvent;
import com.example.my_payplatform.payment.domain.PaymentOrder;
import com.example.my_payplatform.payment.infrastructure.PaymentEventJpaRepository;
import com.example.my_payplatform.payment.infrastructure.PaymentOrderJpaRepository;
import com.example.my_payplatform.payment.service.impl.DefaultPaymentEventService;
import com.example.my_payplatform.payment.service.port.PaymentEventRepository;
import com.example.my_payplatform.payment.service.port.PaymentOrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

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

    @BeforeEach
    void DB_초기화() {
        paymentEventJpaRepository.deleteAll();
        paymentOrderJpaRepository.deleteAll();
    }

    @Test
    void 결제이벤트_및_주문_시작_준비() {
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
    }

    @Test
    void 결제_이벤트_및_주문_시작() {
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
        paymentEventReqDto.setCheckoutId("youShouldSetIdempotencyKeyByUUIDGenerator");

        PaymentEvent paymentEvent = defaultPaymentEventService.readyPayment(paymentEventReqDto);

        // when
        String s = defaultPaymentEventService.executePayment(paymentEventReqDto);

        // then
        Assertions.assertTrue(s.length() > 0);
    }

    @Test
    void 결제_이벤트_및_주문_완료_웹훅_처리() {

    }
}
