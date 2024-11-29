package com.trip.advisor.stripe.service.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.trip.advisor.common.model.dto.StripePaymentRequest;
import com.trip.advisor.stripe.service.model.StripeResponse;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    //stripe API
    //-> productName,amount,quantity, currency
    //-> return sessionId and url


    private final String secret = System.getenv("STRIPE_SECRET");

    public StripeResponse checkoutPayment(StripePaymentRequest request)  {
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
