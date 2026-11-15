package com.aitu.ecommerce;

import com.aitu.ecommerce.adapter.KaspiPayAdapter;
import com.aitu.ecommerce.adapter.KaspiPayService;
import com.aitu.ecommerce.adapter.PaymentGateway;
import com.aitu.ecommerce.adapter.StripeExternalApi;
import com.aitu.ecommerce.adapter.StripePaymentAdapter;
import com.aitu.ecommerce.builder.ProductBuilder;
import com.aitu.ecommerce.facade.ShopFacade;
import com.aitu.ecommerce.model.Product;
import com.aitu.ecommerce.strategy.LoyaltyDiscountStrategy;
import com.aitu.ecommerce.strategy.PercentageDiscountStrategy;
import com.aitu.ecommerce.strategy.ThresholdDiscountStrategy;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("   AITU E-COMMERCE SHOPPING CART SYSTEM (6 DESIGN PATTERNS) ");
        System.out.println("   Team: Matzhan Arlan, Kossaman Madiyar, Rymbek Beknur     ");
        System.out.println("============================================================");

        if (args.length > 0 && "--interactive".equalsIgnoreCase(args[0])) {
            runInteractiveMode();
        } else {
            runAutomatedDemo();
        }
    }

    public static void runAutomatedDemo() {
        ShopFacade shop = new ShopFacade("arlan.matzhan@astanait.edu.kz");

        System.out.println("\n[STEP 1] PATTERN 1: BUILDER - Creating Customized Products");
        Product laptop = new ProductBuilder()
                .setId("PRD-101")
                .setName("ASUS ROG Zephyrus G14")
                .setCategory("Laptops")
                .setBasePrice(1450.00)
                .setColor("Eclipse Gray")
                .setSpecification("32GB RAM / 1TB NVMe / RTX 4070")
                .setWarrantyMonths(24)
                .build();

        Product headphones = new ProductBuilder()
                .setId("PRD-205")
                .setName("Sony WH-1000XM5")
                .setCategory("Audio")
                .setBasePrice(350.00)
                .setColor("Midnight Blue")
                .setSpecification("ANC Wireless")
                .build();

        Product keyboard = new ProductBuilder()
                .setId("PRD-309")
                .setName("Keychron Q1 Pro")
                .setCategory("Peripherals")
                .setBasePrice(190.00)
                .setColor("Carbon Black")
                .setSpecification("Mechanical / Banana Switches")
                .build();

        shop.registerProduct(laptop, 3);
        shop.registerProduct(headphones, 10);
        shop.registerProduct(keyboard, 5);
        shop.printCatalog();

        System.out.println("\n[STEP 2] PATTERNS 2, 4 & 6: DECORATOR + OBSERVER + FACADE - Adding Configured Items");
        shop.addConfiguredItem("PRD-101", 1, false, true, true);
        shop.addConfiguredItem("PRD-205", 1, true, false, false);
        shop.addConfiguredItem("PRD-309", 2, false, false, false);

        System.out.println("\n[STEP 3] PATTERN 4: OBSERVER - Real-Time Stock Restoration & Live Price Drop");
        shop.removeItem("PRD-309");
        shop.updateProductPrice("PRD-205", 310.00);

        System.out.println("\n[STEP 4] PATTERN 3: STRATEGY - Switching Dynamic Discount Algorithms");
        shop.applyDiscount(new PercentageDiscountStrategy("AITU2026", 10.0));
        shop.showCartSummary();

        shop.applyDiscount(new ThresholdDiscountStrategy(1500.00, 200.00));
        shop.showCartSummary();

        shop.applyDiscount(new LoyaltyDiscountStrategy(2500));
        shop.showCartSummary();

        System.out.println("\n[STEP 5] PATTERNS 5 & 6: ADAPTER + FACADE - Unified Checkout via Payment Gateways");
        PaymentGateway kaspiGateway = new KaspiPayAdapter(new KaspiPayService(), 495.0);
        shop.checkout(kaspiGateway, "+7-701-555-0199");

        System.out.println("\n[STEP 6] Secondary Order via Stripe Card Adapter:");
        shop.addConfiguredItem("PRD-309", 1, true, false, false);
        PaymentGateway stripeGateway = new StripePaymentAdapter(new StripeExternalApi());
        shop.checkout(stripeGateway, "tok_visa_aitu_se2505");
    }

    private static void runInteractiveMode() {
        ShopFacade shop = new ShopFacade("student@astanait.edu.kz");
        shop.registerProduct(new ProductBuilder()
                .setId("PRD-101")
                .setName("MacBook Pro 14")
                .setCategory("Laptops")
                .setBasePrice(1999.00)
                .setColor("Space Black")
                .setSpecification("M3 Pro / 18GB / 512GB")
                .setWarrantyMonths(24)
                .build(), 5);
        shop.registerProduct(new ProductBuilder()
                .setId("PRD-205")
                .setName("Sony WH-1000XM5")
                .setCategory("Audio")
                .setBasePrice(350.00)
                .setColor("Midnight Blue")
                .setSpecification("ANC Wireless")
                .build(), 8);
        shop.registerProduct(new ProductBuilder()
                .setId("PRD-309")
                .setName("Keychron Q1 Pro")
                .setCategory("Peripherals")
                .setBasePrice(190.00)
                .setColor("Carbon Black")
                .setSpecification("Mechanical / Banana Switches")
                .build(), 6);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\n--- INTERACTIVE E-COMMERCE MENU ---");
            System.out.println("1. View Store Catalog");
            System.out.println("2. Add Item to Cart (with Decorator Add-Ons)");
            System.out.println("3. Remove Item from Cart");
            System.out.println("4. Apply Discount Strategy");
            System.out.println("5. Admin: Update Product Price (Trigger Observer)");
            System.out.println("6. View Cart Summary");
            System.out.println("7. Checkout with Kaspi Pay (Adapter + Facade)");
            System.out.println("8. Checkout with Stripe Card (Adapter + Facade)");
            System.out.println("0. Exit");
            System.out.print("Select option: ");

            String choice = readLine(scanner);
            if (choice == null) {
                System.out.println("\nInput closed. Goodbye!");
                break;
            }
            switch (choice) {
                case "1":
                    shop.printCatalog();
                    break;
                case "2":
                    System.out.print("Enter Product ID (e.g., PRD-101): ");
                    String id = readLine(scanner);
                    System.out.print("Quantity: ");
                    Integer qty = parseInt(readLine(scanner));
                    if (qty == null || qty <= 0) {
                        System.out.println("Quantity must be a positive whole number.");
                        break;
                    }
                    System.out.print("Add Gift Wrap (+$5/unit)? (y/n): ");
                    boolean wrap = isYes(readLine(scanner));
                    System.out.print("Add Extended Warranty (+10%)? (y/n): ");
                    boolean warranty = isYes(readLine(scanner));
                    System.out.print("Add Express Delivery (+$12.50)? (y/n): ");
                    boolean express = isYes(readLine(scanner));
                    shop.addConfiguredItem(id == null ? "" : id, qty, wrap, warranty, express);
                    break;
                case "3":
                    System.out.print("Enter Product ID to remove: ");
                    String removeId = readLine(scanner);
                    shop.removeItem(removeId == null ? "" : removeId);
                    break;
                case "4":
                    System.out.println("Select Discount: 1) 15% Student  2) $200 off $1500+  3) 2000 Loyalty Pts");
                    String dChoice = readLine(scanner);
                    if ("1".equals(dChoice)) {
                        shop.applyDiscount(new PercentageDiscountStrategy("STUDENT15", 15.0));
                    } else if ("2".equals(dChoice)) {
                        shop.applyDiscount(new ThresholdDiscountStrategy(1500.00, 200.00));
                    } else if ("3".equals(dChoice)) {
                        shop.applyDiscount(new LoyaltyDiscountStrategy(2000));
                    } else {
                        System.out.println("Invalid discount choice.");
                    }
                    break;
                case "5":
                    System.out.print("Enter Product ID: ");
                    String pid = readLine(scanner);
                    System.out.print("Enter New Price ($): ");
                    Double newPrice = parseDouble(readLine(scanner));
                    if (newPrice == null || newPrice < 0) {
                        System.out.println("Price must be a non-negative number.");
                        break;
                    }
                    shop.updateProductPrice(pid == null ? "" : pid, newPrice);
                    break;
                case "6":
                    shop.showCartSummary();
                    break;
                case "7":
                    shop.checkout(new KaspiPayAdapter(new KaspiPayService(), 495.0), "+7-777-123-4567");
                    break;
                case "8":
                    shop.checkout(new StripePaymentAdapter(new StripeExternalApi()), "tok_visa_student");
                    break;
                case "0":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static String readLine(Scanner scanner) {
        return scanner.hasNextLine() ? scanner.nextLine().trim() : null;
    }

    private static boolean isYes(String answer) {
        return "y".equalsIgnoreCase(answer);
    }

    private static Integer parseInt(String text) {
        try {
            return text == null ? null : Integer.valueOf(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Double parseDouble(String text) {
        try {
            return text == null ? null : Double.valueOf(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
