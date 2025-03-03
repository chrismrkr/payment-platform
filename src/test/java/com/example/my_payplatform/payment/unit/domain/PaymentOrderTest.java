package com.example.my_payplatform.payment.unit.domain;

import com.example.my_payplatform.payment.domain.PaymentEvent;
import com.example.my_payplatform.payment.domain.PaymentOrder;
import com.example.my_payplatform.payment.domain.status.PaymentOrderStatus;
import com.example.my_payplatform.payment.infrastructure.entity.PaymentEventEntity;
import com.example.my_payplatform.payment.infrastructure.entity.PaymentOrderEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaymentOrderTest {
    @Test
    void 도메인을_엔티티로_변환() {
        // given
        PaymentEvent paymentEvent = PaymentEvent.builder()
                .checkoutId("0000")
                .buyerInfo("0000")
                .creditCardInfo("0000")
                .isPaymentDone(false)
                .build();
        PaymentOrder paymentOrder = PaymentOrder.builder()
                .paymentOrderId("1234")
                .sellerInfo("1234")
                .amount("1234")
                .currency("1234")
                .paymentOrderStatus(PaymentOrderStatus.NOT_STARTED)
                .ledgerUpdated(false)
                .walletUpdated(false)
                .build();
        // when
        paymentOrder.setPaymentEvent(paymentEvent);
        PaymentOrderEntity entity = paymentOrder.toEntity();
        // then
        Assertions.assertEquals(entity.getPaymentOrderId(), "1234");
        Assertions.assertEquals(entity.getSellerInfo(), "1234");
        Assertions.assertEquals(entity.getAmount(), "1234");
        Assertions.assertEquals(entity.getCurrency(), "1234");
        Assertions.assertEquals(entity.getPaymentOrderStatus(), PaymentOrderStatus.NOT_STARTED);
        Assertions.assertEquals(entity.isLedgerUpdated(), false);
        Assertions.assertEquals(entity.isWalletUpdated(), false);
    }
    @Test
    void 엔티티를_도메인으로_변환() {
        PaymentEventEntity paymentEventEntity = PaymentEventEntity.builder()
                .checkoutId("0000")
                .buyerInfo("0000")
                .creditCardInfo("0000")
                .isPaymentDone(false)
                .build();
        PaymentOrderEntity paymentOrderEntity = PaymentOrderEntity.builder()
                .paymentOrderId("1234")
                .sellerInfo("1234")
                .amount("1234")
                .currency("1234")
                .paymentEventEntity(paymentEventEntity)
                .paymentOrderStatus(PaymentOrderStatus.NOT_STARTED)
                .ledgerUpdated(false)
                .walletUpdated(false)
                .build();
        // when
        PaymentOrder paymentOrder = PaymentOrder.fromWithPaymentEvent(paymentOrderEntity);
        // then
        Assertions.assertEquals(paymentOrder.getPaymentOrderId(), "1234");
        Assertions.assertEquals(paymentOrder.getSellerInfo(), "1234");
        Assertions.assertEquals(paymentOrder.getAmount(), "1234");
        Assertions.assertEquals(paymentOrder.getCurrency(), "1234");
        Assertions.assertEquals(paymentOrder.getPaymentOrderStatus(), PaymentOrderStatus.NOT_STARTED);
        Assertions.assertEquals(paymentOrder.isLedgerUpdated(), false);
        Assertions.assertEquals(paymentOrder.isWalletUpdated(), false);
        Assertions.assertEquals(paymentOrder.getPaymentEvent().getBuyerInfo(), "0000");
    }
}
