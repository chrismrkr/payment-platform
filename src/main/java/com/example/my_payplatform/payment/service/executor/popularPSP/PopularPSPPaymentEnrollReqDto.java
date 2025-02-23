package com.example.my_payplatform.payment.service.executor.popularPSP;

import com.example.my_payplatform.payment.controller.dto.PaymentEventReqDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PopularPSPPaymentEnrollReqDto {
    @Setter
    private String redirectUrl;
    private String amount;
    private String currency;

    private List<PaymentItem> paymentItems = new ArrayList<>();

    @Builder
    private PopularPSPPaymentEnrollReqDto(String redirectUrl, String amount, String currency) {
        this.redirectUrl = redirectUrl;
        this.amount = amount;
        this.currency = currency;
    }

    public void addPaymentItem(PaymentItem paymentItem) {
        this.paymentItems.add(paymentItem);
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class PaymentItem {
        private String paymentOrderId; // 멱등키
        private String sellerInfo;
        @Builder
        private PaymentItem(String paymentOrderId, String sellerInfo) {
            this.paymentOrderId = paymentOrderId;
            this.sellerInfo = sellerInfo;
        }
    }

    public static PopularPSPPaymentEnrollReqDto from(PaymentEventReqDto dto, String redirectUrl) {
        AtomicReference<Integer> totalAmount = new AtomicReference<>(0);
        String currency = dto.getPaymentOrderInfos().get(0).getCurrency();
        dto.getPaymentOrderInfos().forEach(paymentOrderInfo -> {
            totalAmount.updateAndGet(v -> v + Integer.parseInt(paymentOrderInfo.getAmount()));
        });
        PopularPSPPaymentEnrollReqDto build = PopularPSPPaymentEnrollReqDto
                .builder()
                .amount(totalAmount.get().toString())
                .currency(currency)
                .redirectUrl(redirectUrl)
                .build();
        dto.getPaymentOrderInfos().forEach(paymentOrderInfo -> {
            PaymentItem paymentItem = PaymentItem.builder()
                    .paymentOrderId(paymentOrderInfo.getPaymentOrderId())
                    .sellerInfo(paymentOrderInfo.getSellerInfo())
                    .build();
            build.addPaymentItem(paymentItem);
        });
        return build;
    }

}
