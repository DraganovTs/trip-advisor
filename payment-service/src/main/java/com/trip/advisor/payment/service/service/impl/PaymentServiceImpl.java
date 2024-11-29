package com.trip.advisor.payment.service.service.impl;

import com.trip.advisor.payment.service.mapper.PaymentMapper;
import com.trip.advisor.payment.service.model.dto.PaymentDTO;
import com.trip.advisor.payment.service.model.entity.Payment;
import com.trip.advisor.payment.service.repository.PaymentRepository;
import com.trip.advisor.payment.service.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;


    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public List<PaymentDTO> findAll() {
        return this.paymentRepository
                .findAll().stream().map(paymentMapper::mapPaymentToPaymentDTO).collect(Collectors.toList());
    }

    @Override
    public Payment process(PaymentDTO paymentDTO) {


        return null;
    }
}
