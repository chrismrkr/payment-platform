package com.example.my_payplatform.payment.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEventWebhookReqDto {
    private String paymentToken;
    private String eventType;
    private String status;
    @Builder
    public PaymentEventWebhookReqDto(String paymentToken, String eventType, String status) {
        this.paymentToken = paymentToken;
        this.eventType = eventType;
        this.status = status;
    }
}
