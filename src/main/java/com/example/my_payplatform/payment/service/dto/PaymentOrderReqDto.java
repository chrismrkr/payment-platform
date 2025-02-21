package com.example.my_payplatform.payment.service.dto;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PaymentOrderReqDto {
    private String paymentOrderId; // 멱등키
    private String sellerAccount;
    private String amount;
    private String currency;

    @Builder
    public PaymentOrderReqDto(String paymentOrderId, String sellerAccount, String amount, String currency) {
        this.paymentOrderId = paymentOrderId;
        this.sellerAccount = sellerAccount;
        this.amount = amount;
        this.currency = currency;
    }
}
