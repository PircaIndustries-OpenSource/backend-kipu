package com.kipu.backend.Logistics.domain.model.valueobjects.external;

public record UserId(int value) {
    private static final int MIN_VALUE = 0;
    private static final String INVALID_VALUE_MESSAGE_KEY = "user.error.userId.invalidValue";

    public UserId {
        if (value <= MIN_VALUE) {
            throw new IllegalArgumentException(INVALID_VALUE_MESSAGE_KEY);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}