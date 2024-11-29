package com.trip.advisor.payment.service.service.impl;

import com.stripe.exception.StripeException;
import com.trip.advisor.common.model.dto.StripePaymentRequest;
import com.trip.advisor.payment.service.mapper.PaymentMapper;
import com.trip.advisor.payment.service.model.dto.PaymentDTO;
import com.trip.advisor.payment.service.service.PaymentRemoteServiceStripe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentRemoteServiceStripeImpl implements PaymentRemoteServiceStripe {

    private final RestTemplate restTemplate;
    private final String stripeRemoteServiceUrl;
    private final PaymentMapper paymentMapper;
    private final Logger logger = LoggerFactory.getLogger(PaymentRemoteServiceStripeImpl.class);

    public PaymentRemoteServiceStripeImpl(RestTemplate restTemplate,
                                          @Value("${stripe.remoteServiceUrl}") String stripeRemoteServiceUrl, PaymentMapper paymentMapper) {
        this.restTemplate = restTemplate;
        this.stripeRemoteServiceUrl = stripeRemoteServiceUrl;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public void process(PaymentDTO paymentDTO) {
        try {
            StripePaymentRequest stripePaymentRequest = paymentMapper.mapPaymentDTOToStripePaymentRequest(paymentDTO);
            restTemplate.postForObject(stripeRemoteServiceUrl + "/stripe/checkout",
                    stripePaymentRequest, StripePaymentRequest.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
