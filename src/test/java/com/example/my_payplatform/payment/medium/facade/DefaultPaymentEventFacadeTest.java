package com.example.my_payplatform.payment.medium.facade;

import com.example.my_payplatform.payment.controller.dto.PaymentEventReqDto;
import com.example.my_payplatform.payment.facade.DefaultPaymentEventFacade;
import com.example.my_payplatform.payment.infrastructure.PaymentEventJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DefaultPaymentEventFacadeTest {
    @Autowired
    DefaultPaymentEventFacade defaultPaymentEventFacade;
    @Autowired
    PaymentEventJpaRepository paymentEventJpaRepository;

    @BeforeEach
    void init() {
        paymentEventJpaRepository.deleteAll();
    }
    @AfterEach
    void clear() {
        paymentEventJpaRepository.deleteAll();
    }

    @Test
    void 결제_요청() {
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
        String redirectURL = defaultPaymentEventFacade.startPayment(paymentEventReqDto);

        // then
        Assertions.assertTrue(redirectURL.length() > 0);
    }
}
