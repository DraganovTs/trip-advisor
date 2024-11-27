package com.trip.advisor.payment.service.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.trip.advisor.payment.service.model.dto.PaymentRequest;
import com.trip.advisor.payment.service.model.dto.StripeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripeService {

    //stripe API
    //-> productName,amount,quantity, currency
    //-> return sessionId and url
    @Value("${stripe.secretKey}")
    private String secret;

    public StripeResponse checkoutPayment(PaymentRequest request) throws StripeException {
        Stripe.apiKey = secret;

        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(request.getName()).build();

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setProductData(productData)
                .setCurrency(request.getCurrency() == null ? "USD" : request.getCurrency())
                .setUnitAmount(request.getAmount().longValue())
                .build();

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(request.getQuantity())
                .setPriceData(priceData)
                .build();


        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8083/success")
                .setCancelUrl("http://localhost:8083/cancel")
                .addLineItem(lineItem)
                .build();


        Session session = null;
        try {
            session = Session.create(params);
        }catch (StripeException e){
            System.out.println(e.getMessage());
            return StripeResponse.builder()
                    .status("FAILURE")
                    .message(e.getMessage())
                    .build();
        }

    return StripeResponse.builder()
            .status("SUCCESS")
            .message("Payment session created successfully")
            .sessionId(session.getId())
            .sessionUrl(session.getUrl())
            .build();

    }
}
