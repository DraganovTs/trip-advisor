package com.trip.advisor.payment.service.mapper;

import com.trip.advisor.common.model.dto.StripePaymentRequest;
import com.trip.advisor.payment.service.model.dto.PaymentDTO;
import com.trip.advisor.payment.service.model.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentDTO mapPaymentToPaymentDTO(Payment payment) {
        return PaymentDTO.builder()
                .id(payment.getId())
                .accommodationId(payment.getAccommodationId())
                .reservationId(payment.getReservationId())
                .price(payment.getPrice())
                .userId(payment.getUserId())
                .build();
    }

    public StripePaymentRequest mapPaymentDTOToStripePaymentRequest(PaymentDTO paymentDTO) {
        return StripePaymentRequest.builder()
                .name(paymentDTO.getAccommodationName())
                .amount(paymentDTO.getPrice())
                .currency("USD")
                .quantity(1L)
                .build();
    }

    public Payment mapPaymentDTOToPayment(PaymentDTO paymentDTO) {
        return Payment.builder()
                .id(paymentDTO.getId())
                .accommodationId(paymentDTO.getAccommodationId())
                .reservationId(paymentDTO.getReservationId())
                .price(paymentDTO.getPrice())
                .userId(paymentDTO.getUserId())
                .build();
    }
}
