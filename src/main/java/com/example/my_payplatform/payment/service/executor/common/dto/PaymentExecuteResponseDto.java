package com.example.my_payplatform.payment.service.executor.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentExecuteResponseDto {
    private String paymentEventToken;
    private String redirectPageUrl;
    @Builder
    private PaymentExecuteResponseDto(String paymentEventToken, String redirectPageUrl) {
        this.paymentEventToken = paymentEventToken;
        this.redirectPageUrl = redirectPageUrl;
    }
}
