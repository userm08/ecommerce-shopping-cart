package com.aitu.ecommerce.facade;

import com.aitu.ecommerce.adapter.PaymentGateway;
import com.aitu.ecommerce.cart.ShoppingCart;
import com.aitu.ecommerce.decorator.ExpressShippingDecorator;
import com.aitu.ecommerce.decorator.ExtendedWarrantyDecorator;
import com.aitu.ecommerce.decorator.GiftWrapDecorator;
import com.aitu.ecommerce.model.BaseOrderItem;
import com.aitu.ecommerce.model.OrderItem;
import com.aitu.ecommerce.model.Product;
import com.aitu.ecommerce.observer.CartEventType;
import com.aitu.ecommerce.observer.CustomerNotificationService;
import com.aitu.ecommerce.observer.InventoryManager;
import com.aitu.ecommerce.strategy.DiscountStrategy;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShopFacade {
    private final ShoppingCart cart;
    private final InventoryManager inventoryManager;
    private final CustomerNotificationService notificationService;
    private final ReceiptService receiptService;
    private final Map<String, Product> catalog = new LinkedHashMap<>();

    public ShopFacade(String customerEmail) {
        this.cart = new ShoppingCart();
        this.inventoryManager = new InventoryManager();
        this.notificationService = new CustomerNotificationService(customerEmail);
        this.receiptService = new ReceiptService();

        this.cart.addObserver(this.inventoryManager);
        this.cart.addObserver(this.notificationService);
    }

    public void registerProduct(Product product, int initialStock) {
        catalog.put(product.getId(), product);
        inventoryManager.registerProductStock(product.getId(), initialStock);
    }

    public void printCatalog() {
        System.out.println("------------------------------------------------------------");
        System.out.println("STORE CATALOG");
        System.out.println("------------------------------------------------------------");
        for (Product product : catalog.values()) {
            int stock = inventoryManager.getAvailableStock(product.getId());
            System.out.printf("  %s | Stock: %d%n", product, stock);
        }
        System.out.println("------------------------------------------------------------");
    }

    public boolean addConfiguredItem(String productId, int quantity,
                                     boolean giftWrap, boolean extendedWarranty, boolean expressShipping) {
        Product product = catalog.get(productId);
        if (product == null) {
            System.out.println("  [ShopFacade] Product not found: " + productId);
            return false;
        }
        if (!inventoryManager.hasSufficientStock(productId, quantity)) {
            System.out.printf("  [ShopFacade] Insufficient stock for %s (available: %d)%n",
                    product.getName(), inventoryManager.getAvailableStock(productId));
            return false;
        }

        OrderItem item = new BaseOrderItem(product, quantity);
        if (extendedWarranty) {
            item = new ExtendedWarrantyDecorator(item);
        }
        if (giftWrap) {
            item = new GiftWrapDecorator(item);
        }
        if (expressShipping) {
            item = new ExpressShippingDecorator(item);
        }

        cart.addItem(item);
        return true;
    }

    public boolean removeItem(String productId) {
        boolean removed = cart.removeByProductId(productId);
        if (!removed) {
            System.out.println("  [ShopFacade] Item not in cart: " + productId);
        }
        return removed;
    }

    public void applyDiscount(DiscountStrategy strategy) {
        cart.setDiscountStrategy(strategy);
    }

    public void updateProductPrice(String productId, double newPrice) {
        Product product = catalog.get(productId);
        if (product == null) {
            System.out.println("  [ShopFacade] Product not found: " + productId);
            return;
        }
        double oldPrice = product.getBasePrice();
        product.setBasePrice(newPrice);
        System.out.printf("  [ShopFacade] Catalog price updated for %s: $%.2f -> $%.2f%n",
                product.getName(), oldPrice, newPrice);
        if (!cart.publishPriceUpdate(productId, oldPrice, newPrice)) {
            System.out.println("  [ShopFacade] Product is not in the cart, no customer notification needed.");
        }
    }

    public void showCartSummary() {
        System.out.println("------------------------------------------------------------");
        System.out.println("CURRENT SHOPPING CART SUMMARY");
        System.out.println("------------------------------------------------------------");
        if (cart.isEmpty()) {
            System.out.println("  (Cart is empty)");
        } else {
            for (int i = 0; i < cart.getItems().size(); i++) {
                OrderItem item = cart.getItems().get(i);
                System.out.printf("  %d. %-45s $%8.2f%n", i + 1, item.getDescription(), item.getCost());
            }
        }
        System.out.println("------------------------------------------------------------");
        System.out.printf("  Subtotal:                                      $%8.2f%n", cart.calculateSubtotal());
        System.out.printf("  Active Discount: %-30s%n", cart.getDiscountStrategy().getStrategyName());
        System.out.printf("  Final Total:                                   $%8.2f%n", cart.calculateFinalTotal());
        System.out.println("------------------------------------------------------------");
    }

    public boolean checkout(PaymentGateway paymentGateway, String customerAccount) {
        if (cart.isEmpty()) {
            System.out.println("  [ShopFacade] Cannot checkout an empty cart.");
            return false;
        }
        double amount = cart.calculateFinalTotal();
        System.out.printf("%n[ShopFacade] Starting checkout for $%.2f via %s...%n",
                amount, paymentGateway.getProviderName());

        boolean paid = paymentGateway.processPayment(customerAccount, amount);
        if (paid) {
            String receipt = receiptService.generateReceipt(cart, paymentGateway.getProviderName(), customerAccount);
            System.out.println(receipt);
            cart.notifyObservers(CartEventType.CHECKOUT_COMPLETED, "ORDER", cart.getItems().size());
            cart.clearItems();
            System.out.println("[ShopFacade] Order completed. Cart cleared and discount reset.");
        } else {
            System.out.println("[ShopFacade] Payment authorization failed. Stock and cart preserved.");
        }
        return paid;
    }
}
