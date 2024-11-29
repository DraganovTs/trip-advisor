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
}
