package com.example.my_payplatform.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {
    @GetMapping("/payment/result")
    public String renderPaymentResult(@RequestParam("paymentToken") String paymentToken) {
        return "write the page location";
    }
}
