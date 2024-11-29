package com.trip.advisor.stripe.service.controller;

import com.stripe.exception.StripeException;
import com.trip.advisor.common.model.dto.StripePaymentRequest;
import com.trip.advisor.stripe.service.model.StripeResponse;
import com.trip.advisor.stripe.service.service.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StripeController {

    private final StripeService stripeService;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutPayment(@RequestBody StripePaymentRequest request) throws StripeException {
        StripeResponse stripeResponse = stripeService.checkoutPayment(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }
}
