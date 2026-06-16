package com.kipu.backend.iotmonitoring.geolocalization.domain.model.valueobjects;

/**
 * Geolocalization State Enum
 * Represents the operational state of a geolocalized asset.
 */
public enum GeolocalizationSensorState {
    INACTIVE(0),
    ACTIVE(1);

    private final int value;

    GeolocalizationSensorState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * Converts an integer value to its corresponding GeolocalizationState enum.
     * @param value The integer code (e.g., 0, 1)
     * @return The corresponding {@link GeolocalizationSensorState}
     */
    public static GeolocalizationSensorState fromInteger(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("State value must not be null");
        }
        for (GeolocalizationSensorState state : GeolocalizationSensorState.values()) {
            if (state.getValue() == value) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown geolocalization state value: " + value);
    }
}