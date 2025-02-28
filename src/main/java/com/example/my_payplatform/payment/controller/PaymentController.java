package com.example.my_payplatform.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PaymentController {
    @GetMapping("/payment")
    public ModelAndView renderPaymentStartPage() {
        ModelAndView modelAndView = new ModelAndView("payment-ready");
        return modelAndView;
    }
}
