package com.grangeinsurance.vendingmachine;

public class Coin {
    private final double weight;
    private final double diameter;

    public Coin(double weight, double diameter) {
        this.weight = weight;
        this.diameter = diameter;
    }

    public double getWeight() {
        return weight;
    }

    public double getDiameter() {
        return diameter;
    }
}
