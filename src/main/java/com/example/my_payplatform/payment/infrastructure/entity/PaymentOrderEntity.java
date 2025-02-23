package com.example.my_payplatform.payment.infrastructure.entity;

import com.example.my_payplatform.payment.domain.status.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class PaymentOrderEntity {
    @Id
    @Column(name = "payment_order_id")
    private final String paymentOrderId;
    private final String sellerInfo;
    private final String amount;
    private final String currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_event_id")
    private final PaymentEventEntity paymentEventEntity;

    @Enumerated(EnumType.STRING)
    private PaymentOrderStatus paymentOrderStatus;
    private boolean ledgerUpdated;
    private boolean walletUpdated;


    @Builder
    public PaymentOrderEntity(String paymentOrderId, String sellerInfo, String amount, String currency, PaymentEventEntity paymentEventEntity, PaymentOrderStatus paymentOrderStatus, boolean ledgerUpdated, boolean walletUpdated) {
        this.paymentOrderId = paymentOrderId;
        this.sellerInfo = sellerInfo;
        this.amount = amount;
        this.currency = currency;
        this.paymentEventEntity = paymentEventEntity;
        this.paymentOrderStatus = paymentOrderStatus;
        this.ledgerUpdated = ledgerUpdated;
        this.walletUpdated = walletUpdated;
    }
}
