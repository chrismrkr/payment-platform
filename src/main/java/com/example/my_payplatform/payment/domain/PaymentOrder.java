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

    private final PaymentEvent paymentEvent;

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
    public PaymentOrder(String paymentOrderId, String sellerInfo, String amount, String currency, PaymentEvent paymentEvent, PaymentOrderStatus paymentOrderStatus, boolean ledgerUpdated, boolean walletUpdated) {
        this.paymentOrderId = paymentOrderId;
        this.sellerInfo = sellerInfo;
        this.amount = amount;
        this.currency = currency;
        this.paymentEvent = paymentEvent;
        this.paymentOrderStatus = paymentOrderStatus;
        this.ledgerUpdated = ledgerUpdated;
        this.walletUpdated = walletUpdated;
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
                .build();
    }
    public static PaymentOrder fromWithoutPaymentEvent(PaymentOrderEntity entity) {
        return PaymentOrder.builder()
                .paymentOrderId(entity.getPaymentOrderId())
                .sellerInfo(entity.getSellerInfo())
                .amount(entity.getAmount())
                .currency(entity.getCurrency())
                .paymentOrderStatus(entity.getPaymentOrderStatus())
                .ledgerUpdated(entity.isLedgerUpdated())
                .walletUpdated(entity.isWalletUpdated())
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
                .build();
    }
}
