package com.example.my_payplatform.payment.controller;

import com.example.my_payplatform.payment.controller.dto.PaymentEventReqDto;
import com.example.my_payplatform.payment.controller.dto.PaymentEventResultResDto;
import com.example.my_payplatform.payment.controller.dto.PaymentEventWebhookReqDto;
import com.example.my_payplatform.payment.controller.port.PaymentFacade;
import com.example.my_payplatform.payment.domain.status.PaymentResultType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentApiController {
    private final PaymentFacade paymentFacade;

    @PostMapping("/api/payment")
    public ResponseEntity<Void> handlePaymentStartRequest(@RequestBody PaymentEventReqDto dto) {
        String redirectURL = paymentFacade.startPayment(dto);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, redirectURL)
                .build();
    }

    @PostMapping("/api/payment/webhook")
    public ResponseEntity<String> handleWebhookEvent(@RequestBody PaymentEventWebhookReqDto reqDto) {
        paymentFacade.handleWebhookEvent(reqDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body("ok");
    }

    @GetMapping("/api/payment")
    public ResponseEntity<PaymentEventResultResDto> requestPaymentResult(@RequestParam("paymentToken") String paymentToken) {
        PaymentEventResultResDto paymentResult = paymentFacade.findPaymentResult(paymentToken);
        if(paymentResult.getResultType().equals(PaymentResultType.EXECUTING)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(paymentResult);
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, paymentResult.getPaymentResultUrl())
                .build();
    }
}
