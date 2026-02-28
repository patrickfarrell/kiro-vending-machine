package com.grangeinsurance.vendingmachine;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {

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

    public String getDisplay() {
        if (currentAmount == 0) {
            return "INSERT COIN";
        }
        return String.format("$%.2f", currentAmount / 100.0);
    }

    public void insertCoin(double weight, double diameter) {
        int coinValue = identifyCoin(weight, diameter);
        if (coinValue > 0) {
            currentAmount += coinValue;
        } else {
            coinReturn.add(new Coin(weight, diameter));
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
}
