package com.trip.advisor.payment.service.controller;

import com.trip.advisor.payment.service.model.dto.PaymentDTO;
import com.trip.advisor.payment.service.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController( PaymentService paymentService) {
        this.paymentService = paymentService;
    }



    @GetMapping("/all")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {

        List<PaymentDTO> allPayments = paymentService.findAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allPayments);
    }
}
