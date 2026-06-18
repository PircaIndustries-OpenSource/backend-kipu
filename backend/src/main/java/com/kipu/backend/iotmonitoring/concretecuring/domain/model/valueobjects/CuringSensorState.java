package com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects;

/**
 * Concrete Curing Sensor State Enum
 * Represents the state of the IoT Concrete Curing Sensor
 */
public enum CuringSensorState {
    INACTIVE(0),
    MONITORING(1),
    ALERT(2);

    private final int value;

    CuringSensorState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CuringSensorState fromValue(Integer value){
        if (value == null) {
            throw  new IllegalArgumentException("State value must not be null.");
        }
        for (CuringSensorState state : CuringSensorState.values()) {
            if (state.getValue() == value) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown curing sensor state value.");
    }
}
