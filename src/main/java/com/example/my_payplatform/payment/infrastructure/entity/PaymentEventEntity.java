package com.example.my_payplatform.payment.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class PaymentEventEntity {
    @Id
    @Column(name = "checkout_id")
    private final String checkoutId;
    private final String buyerInfo;
    private final String sellerInfo;
    private final String creditCardInfo;
    private boolean isPaymentDone;

    @OneToMany(mappedBy = "paymentEventEntity", fetch = FetchType.LAZY)
    private Set<PaymentOrderEntity> paymentOrderEntities = new HashSet<>();

    @Builder
    public PaymentEventEntity(String checkoutId, String buyerInfo, String sellerInfo, String creditCardInfo, boolean isPaymentDone) {
        this.checkoutId = checkoutId;
        this.buyerInfo = buyerInfo;
        this.sellerInfo = sellerInfo;
        this.creditCardInfo = creditCardInfo;
        this.isPaymentDone = isPaymentDone;
    }
}
