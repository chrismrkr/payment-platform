package com.example.my_payplatform.payment.domain;

import com.example.my_payplatform.payment.domain.status.PaymentOrderStatus;
import com.example.my_payplatform.payment.infrastructure.entity.PaymentEventEntity;
import com.example.my_payplatform.payment.infrastructure.entity.PaymentOrderEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentOrder {
    private final String paymentOrderId;
    private final String sellerInfo;
    private final String amount;
    private final String currency;
    private PaymentOrderStatus paymentOrderStatus;
    private boolean ledgerUpdated;
    private boolean walletUpdated;
    private String paymentToken;

    private final PaymentEvent paymentEvent;

    @Builder
    public PaymentOrder(String paymentOrderId, String sellerInfo, String amount, String currency, PaymentEvent paymentEvent, PaymentOrderStatus paymentOrderStatus, boolean ledgerUpdated, boolean walletUpdated, String paymentToken) {
        this.paymentOrderId = paymentOrderId;
        this.sellerInfo = sellerInfo;
        this.amount = amount;
        this.currency = currency;
        this.paymentEvent = paymentEvent;
        this.paymentOrderStatus = paymentOrderStatus;
        this.ledgerUpdated = ledgerUpdated;
        this.walletUpdated = walletUpdated;
        this.paymentToken = paymentToken;
    }

    public static PaymentOrder from(PaymentOrderEntity entity) {
        return PaymentOrder.builder()
                .paymentOrderId(entity.getPaymentOrderId())
                .sellerInfo(entity.getSellerInfo())
                .amount(entity.getAmount())
                .currency(entity.getCurrency())
                .paymentEvent(PaymentEvent.from(
                        entity.getPaymentEventEntity())
                )
                .paymentOrderStatus(entity.getPaymentOrderStatus())
                .ledgerUpdated(entity.isLedgerUpdated())
                .walletUpdated(entity.isWalletUpdated())
                .paymentToken(entity.getPaymentToken())
                .build();
    }
    public PaymentOrderEntity toEntity() {
        return PaymentOrderEntity.builder()
                .paymentOrderId(this.getPaymentOrderId())
                .sellerInfo(this.getSellerInfo())
                .amount(this.getAmount())
                .currency(this.getCurrency())
                .paymentEventEntity(this.getPaymentEvent().toEntity())
                .paymentOrderStatus(this.getPaymentOrderStatus())
                .ledgerUpdated(this.isLedgerUpdated())
                .walletUpdated(this.isWalletUpdated())
                .paymentToken(this.getPaymentToken())
                .build();
    }
}
