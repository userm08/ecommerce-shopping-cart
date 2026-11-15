package com.aitu.ecommerce.adapter;

public class StripeExternalApi {
    public boolean chargeCardInCents(String cardToken, int amountInCents, String currency) {
        System.out.printf("  [Stripe External SDK] Charging token %s: %d cents (%s %.2f)%n",
                cardToken, amountInCents, currency, amountInCents / 100.0);
        return amountInCents > 0;
    }
}
