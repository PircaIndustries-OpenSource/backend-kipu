package com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.valueobjects;

/**
 * Seismic Control State Enum.
 * Represents the operational and structural alert state of a seismic monitoring node.
 */
public enum SeismicControlSensorState {
    SAFE(1),
    WARNING(2),
    CRITICAL(3);

    private final int value;

    SeismicControlSensorState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * Converts an integer value to its corresponding SeismicControlSensorState enum.
     * @param value The integer code (e.g., 1, 2, 3)
     * @return The corresponding {@link SeismicControlSensorState}
     */
    public static SeismicControlSensorState fromInteger(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("State value must not be null");
        }
        for (SeismicControlSensorState state : SeismicControlSensorState.values()) {
            if (state.getValue() == value) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown seismic control state value: " + value);
    }
}