package com.example.my_payplatform.payment.domain;

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
                .sellerInfo("0000")
                .creditCardInfo("0000")
                .isPaymentDone(false)
                .build();
        PaymentOrder paymentOrder = PaymentOrder.builder()
                .paymentOrderId("1234")
                .buyerAccount("1234")
                .amount("1234")
                .currency("1234")
                .paymentEvent(paymentEvent)
                .paymentOrderStatus(PaymentOrderStatus.NOT_STARTED)
                .ledgerUpdated(false)
                .walletUpdated(false)
                .paymentToken("1234")
                .build();
        // when
        PaymentOrderEntity entity = paymentOrder.toEntity();
        // then
        Assertions.assertEquals(entity.getPaymentOrderId(), "1234");
        Assertions.assertEquals(entity.getBuyerAccount(), "1234");
        Assertions.assertEquals(entity.getAmount(), "1234");
        Assertions.assertEquals(entity.getCurrency(), "1234");
        Assertions.assertEquals(entity.getPaymentOrderStatus(), PaymentOrderStatus.NOT_STARTED);
        Assertions.assertEquals(entity.isLedgerUpdated(), false);
        Assertions.assertEquals(entity.isWalletUpdated(), false);
        Assertions.assertEquals(entity.getPaymentToken(), "1234");
        Assertions.assertEquals(entity.getPaymentEventEntity().getSellerInfo(), "0000");
    }
    @Test
    void 엔티티를_도메인으로_변환() {
        PaymentEventEntity paymentEventEntity = PaymentEventEntity.builder()
                .checkoutId("0000")
                .buyerInfo("0000")
                .sellerInfo("0000")
                .creditCardInfo("0000")
                .isPaymentDone(false)
                .build();
        PaymentOrderEntity paymentOrderEntity = PaymentOrderEntity.builder()
                .paymentOrderId("1234")
                .buyerAccount("1234")
                .amount("1234")
                .currency("1234")
                .paymentEventEntity(paymentEventEntity)
                .paymentOrderStatus(PaymentOrderStatus.NOT_STARTED)
                .ledgerUpdated(false)
                .walletUpdated(false)
                .paymentToken("1234")
                .build();
        // when
        PaymentOrder paymentOrder = PaymentOrder.from(paymentOrderEntity);
        // then
        Assertions.assertEquals(paymentOrder.getPaymentOrderId(), "1234");
        Assertions.assertEquals(paymentOrder.getBuyerAccount(), "1234");
        Assertions.assertEquals(paymentOrder.getAmount(), "1234");
        Assertions.assertEquals(paymentOrder.getCurrency(), "1234");
        Assertions.assertEquals(paymentOrder.getPaymentOrderStatus(), PaymentOrderStatus.NOT_STARTED);
        Assertions.assertEquals(paymentOrder.isLedgerUpdated(), false);
        Assertions.assertEquals(paymentOrder.isWalletUpdated(), false);
        Assertions.assertEquals(paymentOrder.getPaymentToken(), "1234");
        Assertions.assertEquals(paymentOrder.getPaymentEvent().getSellerInfo(), "0000");
    }
}
