package com.grangeinsurance.vendingmachine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VendingMachineTest {

    private VendingMachine vendingMachine;

    @BeforeEach
    void setUp() {
        vendingMachine = new VendingMachine();
    }

    @Test
    void shouldDisplayInsertCoinWhenNoCoinsInserted() {
        assertEquals("INSERT COIN", vendingMachine.getDisplay());
    }

    @Test
    void shouldDisplayAmountWhenQuarterInserted() {
        vendingMachine.insertCoin(5.67, 24.26); // weight in grams, diameter in mm (quarter specs)
        assertEquals("$0.25", vendingMachine.getDisplay());
    }

    @Test
    void shouldDisplayAmountWhenDimeInserted() {
        vendingMachine.insertCoin(2.27, 17.91); // dime specs
        assertEquals("$0.10", vendingMachine.getDisplay());
    }

    @Test
    void shouldDisplayAmountWhenNickelInserted() {
        vendingMachine.insertCoin(5.00, 21.21); // nickel specs
        assertEquals("$0.05", vendingMachine.getDisplay());
    }

    @Test
    void shouldAccumulateMultipleCoins() {
        vendingMachine.insertCoin(5.67, 24.26); // quarter
        vendingMachine.insertCoin(2.27, 17.91); // dime
        vendingMachine.insertCoin(5.00, 21.21); // nickel
        assertEquals("$0.40", vendingMachine.getDisplay());
    }

    @Test
    void shouldRejectInvalidCoins() {
        vendingMachine.insertCoin(2.50, 19.05); // penny specs
        assertEquals("INSERT COIN", vendingMachine.getDisplay());
    }

    @Test
    void shouldPlaceRejectedCoinsInCoinReturn() {
        vendingMachine.insertCoin(2.50, 19.05); // penny
        assertEquals(1, vendingMachine.getCoinReturn().size());
    }
}
