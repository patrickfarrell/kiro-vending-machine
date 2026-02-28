package com.grangeinsurance.vendingmachine;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {

    // Display messages
    private static final String INSERT_COIN_MESSAGE = "INSERT COIN";
    private static final String THANK_YOU_MESSAGE = "THANK YOU";
    private static final String PRICE_FORMAT = "PRICE $%.2f";
    private static final String SOLD_OUT_MESSAGE = "SOLD OUT";
    private static final String EXACT_CHANGE_ONLY_MESSAGE = "EXACT CHANGE ONLY";

    // Coin specifications (weight in grams, diameter in mm)
    private static final double QUARTER_WEIGHT = 5.67;
    private static final double QUARTER_DIAMETER = 24.26;
    private static final double DIME_WEIGHT = 2.27;
    private static final double DIME_DIAMETER = 17.91;
    private static final double NICKEL_WEIGHT = 5.00;
    private static final double NICKEL_DIAMETER = 21.21;
    private static final double TOLERANCE = 0.1;

    private int currentAmount = 0; // Amount in cents
    private List<Coin> coinReturn = new ArrayList<>();
    private List<Coin> insertedCoins = new ArrayList<>();
    private String displayMessage = null;
    private java.util.Map<Product, Integer> inventory = new java.util.EnumMap<>(Product.class);
    private int quartersInMachine = 10;
    private int dimesInMachine = 10;
    private int nickelsInMachine = 10;

    public VendingMachine() {
        initializeInventory();
    }

    public VendingMachine(int quarters, int dimes, int nickels) {
        this.quartersInMachine = quarters;
        this.dimesInMachine = dimes;
        this.nickelsInMachine = nickels;
        initializeInventory();
    }

    private void initializeInventory() {
        inventory.put(Product.COLA, 1);
        inventory.put(Product.CHIPS, 1);
        inventory.put(Product.CANDY, 1);
    }

    public String getDisplay() {
        if (displayMessage != null) {
            String message = displayMessage;
            displayMessage = null;
            return message;
        }
        if (currentAmount == 0) {
            return canMakeChange() ? INSERT_COIN_MESSAGE : EXACT_CHANGE_ONLY_MESSAGE;
        }
        return String.format("$%.2f", currentAmount / 100.0);
    }

    public void insertCoin(double weight, double diameter) {
        int coinValue = identifyCoin(weight, diameter);
        Coin coin = new Coin(weight, diameter);
        if (coinValue > 0) {
            currentAmount += coinValue;
            insertedCoins.add(coin);
        } else {
            coinReturn.add(coin);
        }
    }

    public List<Coin> getCoinReturn() {
        return new ArrayList<>(coinReturn);
    }

    private int identifyCoin(double weight, double diameter) {
        if (matchesCoin(weight, diameter, QUARTER_WEIGHT, QUARTER_DIAMETER)) {
            return 25;
        }
        if (matchesCoin(weight, diameter, DIME_WEIGHT, DIME_DIAMETER)) {
            return 10;
        }
        if (matchesCoin(weight, diameter, NICKEL_WEIGHT, NICKEL_DIAMETER)) {
            return 5;
        }
        return 0; // Invalid coin
    }

    private boolean matchesCoin(double weight, double diameter, double expectedWeight, double expectedDiameter) {
        return Math.abs(weight - expectedWeight) < TOLERANCE && 
               Math.abs(diameter - expectedDiameter) < TOLERANCE;
    }

    public void selectProduct(String product) {
        Product selectedProduct = Product.valueOf(product);
        int stock = inventory.getOrDefault(selectedProduct, 0);
        
        if (stock == 0) {
            displayMessage = SOLD_OUT_MESSAGE;
            return;
        }
        
        if (currentAmount >= selectedProduct.getPrice()) {
            int change = currentAmount - selectedProduct.getPrice();
            currentAmount = 0;
            insertedCoins.clear();
            inventory.put(selectedProduct, stock - 1);
            displayMessage = THANK_YOU_MESSAGE;
            if (change > 0) {
                makeChange(change);
            }
        } else {
            displayMessage = String.format(PRICE_FORMAT, selectedProduct.getPrice() / 100.0);
        }
    }

    private void makeChange(int amount) {
        while (amount >= 25 && quartersInMachine > 0) {
            coinReturn.add(new Coin(QUARTER_WEIGHT, QUARTER_DIAMETER));
            quartersInMachine--;
            amount -= 25;
        }
        while (amount >= 10 && dimesInMachine > 0) {
            coinReturn.add(new Coin(DIME_WEIGHT, DIME_DIAMETER));
            dimesInMachine--;
            amount -= 10;
        }
        while (amount >= 5 && nickelsInMachine > 0) {
            coinReturn.add(new Coin(NICKEL_WEIGHT, NICKEL_DIAMETER));
            nickelsInMachine--;
            amount -= 5;
        }
    }

    private boolean canMakeChange() {
        // Check if we can make change for any product
        // We need to be able to make change for the smallest possible overpayment
        // For CANDY (65 cents), if someone pays with 3 quarters (75 cents), we need 10 cents change
        // For CHIPS (50 cents), if someone pays with 3 quarters (75 cents), we need 25 cents change
        // For COLA (100 cents), if someone pays with 5 quarters (125 cents), we need 25 cents change
        
        // Simplest check: can we make 5 cents (nickel)?
        if (nickelsInMachine > 0 || dimesInMachine > 0 || quartersInMachine > 0) {
            return true;
        }
        return false;
    }

    public void returnCoins() {
        coinReturn.addAll(insertedCoins);
        insertedCoins.clear();
        currentAmount = 0;
    }
}
