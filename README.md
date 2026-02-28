# Vending Machine

A fully functional vending machine implementation built using Test-Driven Development (TDD) with Java 17 and Spring Boot.

## Features

- **Accept Coins**: Accepts nickels, dimes, and quarters. Rejects pennies.
- **Select Product**: Three products available - Cola ($1.00), Chips ($0.50), Candy ($0.65)
- **Make Change**: Returns correct change when product costs less than amount inserted
- **Return Coins**: Allows customers to get their money back
- **Sold Out**: Displays when products are out of stock
- **Exact Change Only**: Displays when machine cannot make change
- **Empty Coin Return**: Customers can take their change
- **Web Interface**: Intuitive web-based UI that looks like a real vending machine

## Running the Application

```bash
./gradlew clean build bootRun
```

Then open your browser to: http://localhost:8080

## Running Tests

```bash
./gradlew test
```

## Technical Details

- Java 17
- Spring Boot 4.0.3
- JUnit 5 for testing
- Thymeleaf for web templates
- No databases or external services required
- Business logic is fully unit-testable without Spring

## TDD Approach

This project was built using the Red-Green-Refactor approach:
1. **Red**: Write a failing test
2. **Green**: Write minimal code to make the test pass
3. **Refactor**: Improve code quality while keeping tests green

Each feature was committed twice:
- Initial implementation (Green phase)
- Refactored version (Refactor phase)

## Architecture

- `VendingMachine`: Core business logic (no Spring dependencies)
- `VendingMachineController`: Spring MVC controller for web interface
- `Product`: Enum defining available products and prices
- `Coin`: Simple data class representing physical coins

The business logic is completely independent of Spring, making it easy to test and reuse.
