package com.example.my_payplatform.payment.controller.dto;

import com.example.my_payplatform.payment.domain.status.PaymentEventType;
import com.example.my_payplatform.payment.domain.status.PaymentResultType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEventResultResDto {
    private PaymentResultType resultType;
    private String paymentResultUrl;
    @Builder
    public PaymentEventResultResDto(PaymentResultType resultType, String paymentResultUrl) {
        this.resultType = resultType;
        this.paymentResultUrl = paymentResultUrl;
    }
}
