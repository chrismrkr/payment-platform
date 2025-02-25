package com.example.my_payplatform.payment.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEventEntity {
    @Id
    @Column(name = "checkout_id")
    private String checkoutId;
    private String buyerInfo;
    private String creditCardInfo;
    private boolean isPaymentDone;
    private String paymentToken;

    @OneToMany(mappedBy = "paymentEventEntity", fetch = FetchType.LAZY)
    private Set<PaymentOrderEntity> paymentOrderEntities = new HashSet<>();

    @Builder
    private PaymentEventEntity(String checkoutId, String buyerInfo, String creditCardInfo, boolean isPaymentDone, String paymentToken) {
        this.checkoutId = checkoutId;
        this.buyerInfo = buyerInfo;
        this.creditCardInfo = creditCardInfo;
        this.isPaymentDone = isPaymentDone;
        this.paymentToken = paymentToken;
    }
}
