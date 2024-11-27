package com.trip.advisor.payment.service.controller;

import com.stripe.exception.StripeException;
import com.trip.advisor.payment.service.model.dto.PaymentRequest;
import com.trip.advisor.payment.service.model.dto.StripeResponse;
import com.trip.advisor.payment.service.service.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final StripeService stripeService;

    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutPayment(@RequestBody PaymentRequest request) throws StripeException {
        StripeResponse stripeResponse = stripeService.checkoutPayment(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }
}
