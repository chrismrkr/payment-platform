package com.example.my_payplatform.payment.controller.dto;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PaymentEventReqDto {
    private String checkoutId;
    private String buyerInfo; // 소비자
    private String sellerInfo; // 판매자
    private String creditCardInfo; // PSP(카드사) 관련 정보

    private String amount;
    private String currency;

    @Builder
    public PaymentEventReqDto(String checkoutId, String buyerInfo, String sellerInfo, String creditCardInfo, String amount, String currency) {
        this.checkoutId = checkoutId;
        this.buyerInfo = buyerInfo;
        this.sellerInfo = sellerInfo;
        this.creditCardInfo = creditCardInfo;
        this.amount = amount;
        this.currency = currency;
    }
}
