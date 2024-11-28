package com.trip.advisor.payment.service.service;


import com.trip.advisor.payment.service.model.entity.Payment;

import java.util.List;

public interface PaymentService {

    List<Payment> findAll();

    Payment process(Payment payment);
}
