package com.sni.projekat.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
    public boolean isPaymentSucceeded(String paymentIntentId) {
        try {
            PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);
            return "succeeded".equals(intent.getStatus());
        } catch (StripeException e) {
            return false;
        }
    }

    public Session dobaviSesiju(String sessionId) throws Exception {
        return Session.retrieve(sessionId);
    }
}
