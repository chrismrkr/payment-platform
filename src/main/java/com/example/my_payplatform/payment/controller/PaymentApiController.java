package com.example.my_payplatform.payment.controller;

import com.example.my_payplatform.payment.controller.dto.PaymentEventReqDto;
import com.example.my_payplatform.payment.controller.port.PaymentFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentApiController {
    private final PaymentFacade paymentFacade;


    @PostMapping("/api/payment")
    public ResponseEntity<Void> handlePaymentStartRequest(@RequestBody PaymentEventReqDto dto) {
        String redirectURL = paymentFacade.startPayment(dto);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, redirectURL)
                .build();
    }
}
