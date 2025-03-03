package com.example.my_payplatform.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@CrossOrigin(origins = "*")
public class PaymentController {
    @GetMapping("/payment")
    public ModelAndView renderPaymentStartPage() {
        ModelAndView modelAndView = new ModelAndView("payment-ready");
        return modelAndView;
    }

    @GetMapping("/payment/result")
    public ModelAndView renderPaymentAwaitingPage() {
        ModelAndView modelAndView = new ModelAndView("payment-waiting");
        return modelAndView;
    }
    @GetMapping("/payment/success")
    public ModelAndView renderPaymentSuccessPage() {
        ModelAndView modelAndView = new ModelAndView("payment-success");
        return modelAndView;
    }
    @GetMapping("/payment/failure")
    public ModelAndView renderPaymentFailurePage() {
        ModelAndView modelAndView = new ModelAndView("payment-failure");
        return modelAndView;
    }
}
