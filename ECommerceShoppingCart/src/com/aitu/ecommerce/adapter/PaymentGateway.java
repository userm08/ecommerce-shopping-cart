package com.aitu.ecommerce.adapter;

public interface PaymentGateway {
    String getProviderName();
    boolean processPayment(String customerAccount, double amountUsd);
}
