package com.kipu.backend.iotmonitoring.hopperwatch.domain.model.valueobjects;

/**
 * Hopper State Enum
 * Represents the operational and level state of a hopper monitor.
 */
public enum HopperSensorState {
    OK(1),       // Lectura estable y segura (ej. Piedra Chancada con 80%)
    ALERT(2),    // Nivel por debajo del límite de seguridad (ej. Arena Fina con 12%)
    CRITICAL(3); // Tolva completamente vacía o sensor desconectado

    private final int value;

    HopperSensorState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * Converts an integer value to its corresponding HopperState enum.
     * @param value The integer code (e.g., 1, 2, 3)
     * @return The corresponding {@link HopperSensorState}
     */
    public static HopperSensorState fromValue(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("State value must not be null");
        }
        for (HopperSensorState state : HopperSensorState.values()) {
            if (state.getValue() == value) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown hopper state value: " + value);
    }
}