package com.bsuir.controller;

import com.bsuir.dto.ChargeRequest;
import com.bsuir.entity.User;
import com.bsuir.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER')")
    public String charge(ChargeRequest chargeRequest, @AuthenticationPrincipal User user) {
        chargeRequest.setCurrency("USD");
        paymentService.charge(chargeRequest, user.getUsername());
        return "redirect:/user-info";
    }
}