package com.aitu.ecommerce.adapter;

public class KaspiPayAdapter implements PaymentGateway {
    private final KaspiPayService kaspiService;
    private final double usdToKztRate;

    public KaspiPayAdapter(KaspiPayService kaspiService, double usdToKztRate) {
        this.kaspiService = kaspiService;
        this.usdToKztRate = usdToKztRate;
    }

    @Override
    public String getProviderName() {
        return "Kaspi Pay (Adapted)";
    }

    @Override
    public boolean processPayment(String customerAccount, double amountUsd) {
        double amountKzt = amountUsd * usdToKztRate;
        String txId = kaspiService.sendKaspiTransfer(customerAccount, amountKzt);
        System.out.println("  [KaspiPayAdapter] Confirmed Transaction ID: " + txId);
        return txId != null && !txId.isEmpty();
    }
}
