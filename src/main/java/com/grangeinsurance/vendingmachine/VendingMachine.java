package com.grangeinsurance.vendingmachine;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {

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
        // Quarter: 5.67g, 24.26mm
        if (Math.abs(weight - 5.67) < 0.1 && Math.abs(diameter - 24.26) < 0.1) {
            return 25;
        }
        // Dime: 2.27g, 17.91mm
        if (Math.abs(weight - 2.27) < 0.1 && Math.abs(diameter - 17.91) < 0.1) {
            return 10;
        }
        // Nickel: 5.00g, 21.21mm
        if (Math.abs(weight - 5.00) < 0.1 && Math.abs(diameter - 21.21) < 0.1) {
            return 5;
        }
        return 0; // Invalid coin
    }
}
