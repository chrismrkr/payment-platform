package com.example.my_payplatform.payment.domain;

import com.example.my_payplatform.payment.infrastructure.entity.PaymentEventEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentEvent {
    private final String checkoutId;
    private final String buyerInfo;
    private final String sellerInfo;
    private final String creditCardInfo;
    private boolean isPaymentDone;
    @Builder
    public PaymentEvent(String checkoutId, String buyerInfo, String sellerInfo, String creditCardInfo, boolean isPaymentDone) {
        this.checkoutId = checkoutId;
        this.buyerInfo = buyerInfo;
        this.sellerInfo = sellerInfo;
        this.creditCardInfo = creditCardInfo;
        this.isPaymentDone = isPaymentDone;
    }

    public static PaymentEvent from(PaymentEventEntity entity) {
        return PaymentEvent.builder()
                .checkoutId(entity.getCheckoutId())
                .buyerInfo(entity.getBuyerInfo())
                .sellerInfo(entity.getSellerInfo())
                .creditCardInfo(entity.getCreditCardInfo())
                .isPaymentDone(entity.isPaymentDone())
                .build();
    }
    public PaymentEventEntity toEntity() {
        return PaymentEventEntity.builder()
                .checkoutId(this.getCheckoutId())
                .buyerInfo(this.getBuyerInfo())
                .sellerInfo(this.getSellerInfo())
                .creditCardInfo(this.getCreditCardInfo())
                .isPaymentDone(this.isPaymentDone())
                .build();
    }
}
