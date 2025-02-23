package com.example.my_payplatform.payment.domain;

import com.example.my_payplatform.payment.infrastructure.entity.PaymentEventEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PaymentEvent {
    private final String checkoutId;
    private final String buyerInfo;
    private final String creditCardInfo;
    private boolean isPaymentDone;

    @Setter
    private String paymentToken;

    @Builder
    public PaymentEvent(String checkoutId, String buyerInfo, String creditCardInfo, boolean isPaymentDone, String paymentToken) {
        this.checkoutId = checkoutId;
        this.buyerInfo = buyerInfo;
        this.creditCardInfo = creditCardInfo;
        this.isPaymentDone = isPaymentDone;
    }

    public static PaymentEvent from(PaymentEventEntity entity) {
        return PaymentEvent.builder()
                .checkoutId(entity.getCheckoutId())
                .buyerInfo(entity.getBuyerInfo())
                .creditCardInfo(entity.getCreditCardInfo())
                .isPaymentDone(entity.isPaymentDone())
                .paymentToken(entity.getPaymentToken())
                .build();
    }
    public PaymentEventEntity toEntity() {
        return PaymentEventEntity.builder()
                .checkoutId(this.getCheckoutId())
                .buyerInfo(this.getBuyerInfo())
                .creditCardInfo(this.getCreditCardInfo())
                .isPaymentDone(this.isPaymentDone())
                .paymentToken(this.getPaymentToken())
                .build();
    }
}
