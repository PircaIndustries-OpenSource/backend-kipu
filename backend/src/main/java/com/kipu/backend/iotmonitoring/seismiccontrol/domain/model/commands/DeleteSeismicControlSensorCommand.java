package com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.commands;

public record DeleteSeismicControlSensorCommand(Long id) {
    public DeleteSeismicControlSensorCommand {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID is required");
    }
}
