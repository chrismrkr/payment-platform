package com.example.my_payplatform.payment.controller.dto;

import lombok.*;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PaymentEventReqDto {
    @Setter
    private String checkoutId;
    private String buyerInfo; // 소비자
    private String creditCardInfo; // PSP(카드사) 관련 정보
    private String buyerAccount;

    private List<PaymentOrderInfo> paymentOrderInfos;

    @Builder
    public PaymentEventReqDto(String buyerInfo, String creditCardInfo, String buyerAccount, List<PaymentOrderInfo> paymentOrderInfos) {
        this.buyerInfo = buyerInfo;
        this.creditCardInfo = creditCardInfo;
        this.buyerAccount = buyerAccount;
        this.paymentOrderInfos = paymentOrderInfos;
    }


    @NoArgsConstructor
    @Getter
    public static class PaymentOrderInfo {
        @Setter
        private String paymentOrderId;
        private String sellerInfo; // 판매자
        private String amount;
        private String currency;
        @Builder
        public PaymentOrderInfo(String sellerInfo, String amount, String currency) {
            this.sellerInfo = sellerInfo;
            this.amount = amount;
            this.currency = currency;
        }
    }
}
