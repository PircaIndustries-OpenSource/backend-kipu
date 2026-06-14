package com.kipu.backend.Logistics.domain.model.valueobjects;

public record Quantity(int value) {
    private static final int MIN_VALUE = 0;
    private static final String INVALID_VALUE_MESSAGE_KEY = "quantity.error.quantity.invalidValue";
    private static final String INSUFFICIENT_STOCK_MESSAGE_KEY = "quantity.error.quantity.insufficientStock";

    public Quantity {
        if (value < MIN_VALUE) {
            throw new IllegalArgumentException(INVALID_VALUE_MESSAGE_KEY);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}