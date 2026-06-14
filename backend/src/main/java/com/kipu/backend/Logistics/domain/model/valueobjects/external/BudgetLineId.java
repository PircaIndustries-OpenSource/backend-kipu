package com.kipu.backend.Logistics.domain.model.valueobjects.external;

public record BudgetLineId(int value) {
    private static final int MIN_VALUE = 0;
    private static final String INVALID_VALUE_MESSAGE_KEY = "budget.line.error.budgetLineId.invalidValue";

    public BudgetLineId {
        if (value <= MIN_VALUE) {
            throw new IllegalArgumentException(INVALID_VALUE_MESSAGE_KEY);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}