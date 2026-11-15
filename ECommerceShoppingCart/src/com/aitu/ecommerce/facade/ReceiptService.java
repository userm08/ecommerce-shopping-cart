package com.aitu.ecommerce.facade;

import com.aitu.ecommerce.cart.ShoppingCart;
import com.aitu.ecommerce.model.OrderItem;

public class ReceiptService {
    private int orderSequence = 1000;

    public String generateReceipt(ShoppingCart cart, String paymentProvider, String customerAccount) {
        int orderId = ++orderSequence;
        StringBuilder sb = new StringBuilder();
        sb.append("============================================================\n");
        sb.append(String.format("OFFICIAL ORDER RECEIPT  #ORD-%d%n", orderId));
        sb.append("------------------------------------------------------------\n");
        for (int i = 0; i < cart.getItems().size(); i++) {
            OrderItem item = cart.getItems().get(i);
            sb.append(String.format("  %d. %-45s $%8.2f%n", i + 1, item.getDescription(), item.getCost()));
        }
        double subtotal = cart.calculateSubtotal();
        double finalTotal = cart.calculateFinalTotal();
        double saved = subtotal - finalTotal;
        sb.append("------------------------------------------------------------\n");
        sb.append(String.format("  Subtotal:                                      $%8.2f%n", subtotal));
        sb.append(String.format("  Discount (%s): -$%7.2f%n", cart.getDiscountStrategy().getStrategyName(), saved));
        sb.append(String.format("  Total Paid:                                    $%8.2f%n", finalTotal));
        sb.append(String.format("  Payment Method: %s (%s)%n", paymentProvider, customerAccount));
        sb.append("============================================================");
        return sb.toString();
    }
}
