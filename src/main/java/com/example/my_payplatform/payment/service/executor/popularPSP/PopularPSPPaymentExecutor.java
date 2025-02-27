package com.example.my_payplatform.payment.service.executor.popularPSP;

import com.example.my_payplatform.payment.controller.dto.PaymentEventReqDto;
import com.example.my_payplatform.payment.domain.PaymentEvent;
import com.example.my_payplatform.payment.service.executor.common.dto.PaymentExecuteResponseDto;
import com.example.my_payplatform.payment.service.impl.DefaultPaymentEventService;
import com.example.my_payplatform.payment.utils.HttpClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:popularpsp.properties")
public class PopularPSPPaymentExecutor implements DefaultPaymentEventService.PaymentExecutor {
    private final Environment environment;
    private final HttpClientUtil httpClientUtil;
    @Override
    public PaymentExecuteResponseDto enrollPayment(PaymentEventReqDto dto) {
        PopularPSPPaymentEnrollReqDto popularPSPPaymentEnrollReqDto = PopularPSPPaymentEnrollReqDto.from(
                                                dto, environment.getProperty("popularpsp.redirect.url"));

        Map<String, String> httpHeader = new HashMap<>();
        httpHeader.put(environment.getProperty("popularpsp.api-server.idempotency-key-name"), dto.getCheckoutId());

        ResponseEntity<PopularPSPPaymentEnrollResDto> httpResult = httpClientUtil.post(
                environment.getProperty("popularpsp.api-server.url"), PopularPSPPaymentEnrollResDto.class,
                popularPSPPaymentEnrollReqDto,
                httpHeader, MediaType.APPLICATION_JSON);

        return PaymentExecuteResponseDto
                .builder()
                .paymentEventToken(Objects.requireNonNull(httpResult.getBody()).getPaymentToken())
                .redirectPageUrl(environment.getProperty("popularpsp.pay-page.url"))
                .build();
    }
}
