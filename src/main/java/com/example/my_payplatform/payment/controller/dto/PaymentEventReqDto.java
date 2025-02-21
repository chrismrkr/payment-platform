package com.example.my_payplatform.payment.controller.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PaymentEventReqDto {
    @Setter
    private String checkoutId;
    private String buyerInfo; // 소비자
    private String creditCardOInfo; // PSP(카드사) 관련 정보

    private List<PaymentOrderInfo> paymentOrderInfos;

    @Builder
    public PaymentEventReqDto(String buyerInfo, String creditCardOInfo, List<PaymentOrderInfo> paymentOrderInfos) {
        this.buyerInfo = buyerInfo;
        this.creditCardOInfo = creditCardOInfo;
        this.paymentOrderInfos = paymentOrderInfos;
    }

    @Data
    @NoArgsConstructor
    @Getter
    private static class PaymentOrderInfo {
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
