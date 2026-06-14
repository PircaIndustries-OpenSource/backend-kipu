package com.kipu.backend.Logistics.domain.model.valueobjects;

public record WarehouseLocation(String value) {
    private static final String INVALID_FORMAT_MESSAGE_KEY = "warehouse.location.error.invalidFormat";
    private static final String NOT_BLANK_MESSAGE_KEY = "warehouse.location.error.notBlank";
    private static final String AISLE_EMPTY_MESSAGE_KEY = "warehouse.location.error.aisleEmpty";
    private static final String RACK_EMPTY_MESSAGE_KEY = "warehouse.location.error.rackEmpty";
    private static final String SHELF_EMPTY_MESSAGE_KEY = "warehouse.location.error.shelfEmpty";

    public WarehouseLocation {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }

        String trimmedValue = value.trim();
        String[] parts = trimmedValue.split("-");

        if (parts.length != 3) {
            throw new IllegalArgumentException(INVALID_FORMAT_MESSAGE_KEY);
        }

        String aisle = parts[0];
        String rack = parts[1];
        String shelf = parts[2];

        if (aisle == null || aisle.isBlank()) {
            throw new IllegalArgumentException(AISLE_EMPTY_MESSAGE_KEY);
        }

        if (rack == null || rack.isBlank()) {
            throw new IllegalArgumentException(RACK_EMPTY_MESSAGE_KEY);
        }

        if (shelf == null || shelf.isBlank()) {
            throw new IllegalArgumentException(SHELF_EMPTY_MESSAGE_KEY);
        }
    }
    @Override
    public String toString() {
        return value();
    }
}