package com.example.my_payplatform.payment.service.executor.popularPSP;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopularPSPPaymentEnrollResDto {
    private String resultCode;
    private String paymentToken;
}
