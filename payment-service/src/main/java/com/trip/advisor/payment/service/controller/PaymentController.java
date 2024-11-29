package com.trip.advisor.payment.service.controller;

import com.stripe.exception.StripeException;
import com.trip.advisor.payment.service.model.dto.PaymentDTO;
import com.trip.advisor.common.model.dto.StripePaymentRequest;
import com.trip.advisor.payment.service.model.dto.StripeResponse;
import com.trip.advisor.payment.service.service.PaymentService;
import com.trip.advisor.payment.service.service.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final StripeService stripeService;
    private final PaymentService paymentService;

    public PaymentController(StripeService stripeService, PaymentService paymentService) {
        this.stripeService = stripeService;
        this.paymentService = paymentService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutPayment(@RequestBody StripePaymentRequest request) throws StripeException {
        StripeResponse stripeResponse = stripeService.checkoutPayment(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {

        List<PaymentDTO> allPayments = paymentService.findAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allPayments);
    }
}
