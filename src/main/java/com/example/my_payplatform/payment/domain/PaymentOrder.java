package com.example.my_payplatform.payment.domain;

import com.example.my_payplatform.payment.domain.status.PaymentOrderStatus;
import com.example.my_payplatform.payment.infrastructure.entity.PaymentEventEntity;
import com.example.my_payplatform.payment.infrastructure.entity.PaymentOrderEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PaymentOrder {
    private final String paymentOrderId;
    private final String sellerInfo;
    private final String amount;
    private final String currency;
    private PaymentOrderStatus paymentOrderStatus;
    private boolean ledgerUpdated;
    private boolean walletUpdated;

    private PaymentEvent paymentEvent;

    public void changeStatus(PaymentOrderStatus status) {
        this.paymentOrderStatus = status;
    }
    public void successLedgerUpdate() {
        this.ledgerUpdated = true;
    }
    public void successWalletUpdate() {
        this.walletUpdated = true;
    }

    @Builder
    public PaymentOrder(String paymentOrderId, String sellerInfo, String amount, String currency, PaymentOrderStatus paymentOrderStatus, boolean ledgerUpdated, boolean walletUpdated, PaymentEvent paymentEvent) {
        this.paymentOrderId = paymentOrderId;
        this.sellerInfo = sellerInfo;
        this.amount = amount;
        this.currency = currency;
        this.paymentOrderStatus = paymentOrderStatus;
        this.ledgerUpdated = ledgerUpdated;
        this.walletUpdated = walletUpdated;
        this.paymentEvent = paymentEvent;
    }

    public PaymentOrderEntity toEntity() {
        return PaymentOrderEntity.builder()
                .paymentOrderId()
                .sellerInfo()
                .paymentOrderStatus()
                .amount()
                .currency()
                .ledgerUpdated()
                .walletUpdated()
                .build();
    }
    public PaymentOrderEntity toEntityWith
}
