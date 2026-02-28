package com.grangeinsurance.vendingmachine;

public enum Product {
    COLA(100),
    CHIPS(50),
    CANDY(65);

    private final int price; // Price in cents

    Product(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
