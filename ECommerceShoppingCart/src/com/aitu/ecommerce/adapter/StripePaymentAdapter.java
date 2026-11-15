package com.aitu.ecommerce.adapter;

public class StripePaymentAdapter implements PaymentGateway {
    private final StripeExternalApi stripeApi;

    public StripePaymentAdapter(StripeExternalApi stripeApi) {
        this.stripeApi = stripeApi;
    }

    @Override
    public String getProviderName() {
        return "Stripe Card Gateway (Adapted)";
    }

    @Override
    public boolean processPayment(String customerAccount, double amountUsd) {
        int cents = (int) Math.round(amountUsd * 100);
        boolean success = stripeApi.chargeCardInCents(customerAccount, cents, "USD");
        if (success) {
            System.out.println("  [StripePaymentAdapter] Card payment authorized.");
        }
        return success;
    }
}
