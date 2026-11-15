package com.aitu.ecommerce.adapter;

public class KaspiPayService {
    public String sendKaspiTransfer(String phoneNumber, double amountKzt) {
        System.out.printf("  [KaspiPay External API] Initiating QR/Phone transfer for %s: %.2f KZT%n",
                phoneNumber, amountKzt);
        return "KASPI_TXN_" + System.currentTimeMillis();
    }
}
