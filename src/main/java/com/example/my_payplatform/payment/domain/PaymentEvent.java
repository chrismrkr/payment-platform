package com.example.my_payplatform.payment.domain;

import com.example.my_payplatform.payment.infrastructure.entity.PaymentEventEntity;
import com.example.my_payplatform.payment.infrastructure.entity.PaymentOrderEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
public class PaymentEvent {
    private final String checkoutId;
    private final String buyerInfo;
    private final String creditCardInfo;
    private boolean isPaymentDone;

    private List<PaymentOrder> paymentOrders = new ArrayList<>();

    @Setter
    private String paymentToken;

    public void donePayment() {
        this.isPaymentDone = true;
    }

    @Builder
    public PaymentEvent(String checkoutId, String buyerInfo, String creditCardInfo, boolean isPaymentDone, String paymentToken) {
        this.checkoutId = checkoutId;
        this.buyerInfo = buyerInfo;
        this.creditCardInfo = creditCardInfo;
        this.isPaymentDone = isPaymentDone;
        this.paymentToken = paymentToken;
    }

    public PaymentEventEntity toEntity() {
        return PaymentEventEntity.builder()
                .checkoutId(this.getCheckoutId())
                .buyerInfo(this.getBuyerInfo())
                .isPaymentDone(this.isPaymentDone())
                .creditCardInfo(this.getCreditCardInfo())
                .paymentToken(this.getPaymentToken())
                .build();
    }
    public static PaymentEvent from(PaymentEventEntity entity) {
        return PaymentEvent.builder()
                .checkoutId(entity.getCheckoutId())
                .buyerInfo(entity.getBuyerInfo())
                .isPaymentDone(entity.isPaymentDone())
                .creditCardInfo(entity.getCreditCardInfo())
                .paymentToken(entity.getPaymentToken())
                .build();
    }
    public static PaymentEvent fromWithPaymentOrder(PaymentEventEntity entity) {
        PaymentEvent paymentEvent = PaymentEvent.builder()
                .checkoutId(entity.getCheckoutId())
                .buyerInfo(entity.getBuyerInfo())
                .isPaymentDone(entity.isPaymentDone())
                .creditCardInfo(entity.getCreditCardInfo())
                .paymentToken(entity.getPaymentToken())
                .build();
        entity.getPaymentOrderEntities().forEach(paymentOrderEntity -> {
            PaymentOrder paymentOrder = PaymentOrder.from(paymentOrderEntity);
            paymentEvent.getPaymentOrders().add(paymentOrder);
            paymentOrder.setPaymentEvent(paymentEvent);
        });
        return paymentEvent;
    }

}
