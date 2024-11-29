package com.trip.advisor.payment.service.service;

import com.trip.advisor.payment.service.model.dto.PaymentDTO;

public interface PaymentRemoteServiceStripe {

    void process(PaymentDTO paymentDTO);
}
