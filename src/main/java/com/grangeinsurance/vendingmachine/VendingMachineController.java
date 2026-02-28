package com.grangeinsurance.vendingmachine;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VendingMachineController {

    private final VendingMachine vendingMachine = new VendingMachine();

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("display", vendingMachine.getDisplay());
        model.addAttribute("coinReturnCount", vendingMachine.getCoinReturn().size());
        return "index";
    }

    @PostMapping("/insertCoin")
    @ResponseBody
    public VendingMachineState insertCoin(@RequestParam String coinType) {
        switch (coinType) {
            case "QUARTER":
                vendingMachine.insertCoin(5.67, 24.26);
                break;
            case "DIME":
                vendingMachine.insertCoin(2.27, 17.91);
                break;
            case "NICKEL":
                vendingMachine.insertCoin(5.00, 21.21);
                break;
        }
        return getState();
    }

    @PostMapping("/selectProduct")
    @ResponseBody
    public VendingMachineState selectProduct(@RequestParam String product) {
        vendingMachine.selectProduct(product);
        return getState();
    }

    @PostMapping("/returnCoins")
    @ResponseBody
    public VendingMachineState returnCoins() {
        vendingMachine.returnCoins();
        return getState();
    }

    @PostMapping("/emptyCoinReturn")
    @ResponseBody
    public VendingMachineState emptyCoinReturn() {
        vendingMachine.emptyCoinReturn();
        return getState();
    }

    @GetMapping("/state")
    @ResponseBody
    public VendingMachineState getState() {
        return new VendingMachineState(
            vendingMachine.getDisplay(),
            vendingMachine.getCoinReturn().size()
        );
    }

    public static class VendingMachineState {
        private String display;
        private int coinReturnCount;

        public VendingMachineState(String display, int coinReturnCount) {
            this.display = display;
            this.coinReturnCount = coinReturnCount;
        }

        public String getDisplay() {
            return display;
        }

        public int getCoinReturnCount() {
            return coinReturnCount;
        }
    }
}
