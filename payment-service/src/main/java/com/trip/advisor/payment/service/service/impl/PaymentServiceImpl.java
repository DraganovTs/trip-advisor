package com.trip.advisor.payment.service.service.impl;

import com.trip.advisor.payment.service.model.entity.Payment;
import com.trip.advisor.payment.service.service.PaymentService;

import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    @Override
    public List<Payment> findAll() {
        return List.of();
    }

    @Override
    public Payment process(Payment payment) {
        return null;
    }
}
