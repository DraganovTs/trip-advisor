package com.trip.advisor.payment.service.service;


import com.trip.advisor.payment.service.model.dto.PaymentDTO;

import java.util.List;

public interface PaymentService {

    List<PaymentDTO> findAll();

    PaymentDTO process(PaymentDTO paymentDTO);
}
