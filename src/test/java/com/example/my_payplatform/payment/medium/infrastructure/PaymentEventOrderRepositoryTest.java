package com.example.my_payplatform.payment.medium.infrastructure;

import com.example.my_payplatform.payment.domain.PaymentEvent;
import com.example.my_payplatform.payment.domain.PaymentOrder;
import com.example.my_payplatform.payment.domain.status.PaymentOrderStatus;
import com.example.my_payplatform.payment.infrastructure.PaymentEventJpaRepository;
import com.example.my_payplatform.payment.infrastructure.PaymentOrderJpaRepository;
import com.example.my_payplatform.payment.service.port.PaymentEventRepository;
import com.example.my_payplatform.payment.service.port.PaymentOrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@SpringBootTest
public class PaymentEventOrderRepositoryTest {
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
    void init() {
        paymentEventJpaRepository.deleteAll();
        paymentOrderJpaRepository.deleteAll();
    }

    @Test
    @Transactional
    void PaymentEvent_및_PaymentOrder_저장_및_조회() {
        // given
        String checkoutId = "1";
        PaymentEvent paymentEvent = PaymentEvent.builder()
                .checkoutId(checkoutId)
                .buyerInfo("kim")
                .creditCardInfo("abcCard123")
                .isPaymentDone(false)
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
                .paymentEvent(paymentEvent)
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
                .paymentEvent(paymentEvent)
                .build();
        // when
        paymentEventRepository.save(paymentEvent);
        paymentOrderRepository.save(paymentOrder1);
        paymentOrderRepository.save(paymentOrder2);
        em.flush();

        // then
        PaymentEvent byCheckoutId = paymentEventRepository.findByCheckoutId(checkoutId);
        Assertions.assertEquals(checkoutId, byCheckoutId.getCheckoutId());
        List<PaymentOrder> paymentOrderList = paymentOrderRepository.findByCheckoutId(checkoutId);
        Assertions.assertEquals(2, paymentOrderList.size());
    }

    @Test
    void PaymentEvent_중복_저장_불가() {
        // given
        String checkoutId = "1";
        PaymentEvent paymentEvent = PaymentEvent.builder()
                .checkoutId(checkoutId)
                .buyerInfo("kim")
                .creditCardInfo("abcCard123")
                .isPaymentDone(false)
                .build();
        PaymentEvent paymentEvent2 = PaymentEvent.builder()
                .checkoutId(checkoutId)
                .buyerInfo("kim")
                .creditCardInfo("abcCard123")
                .isPaymentDone(false)
                .build();
        // when
        paymentEventRepository.persistWithPaymentOrder(paymentEvent);
        // then
        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> paymentEventRepository.persistWithPaymentOrder(paymentEvent2));
    }

}
