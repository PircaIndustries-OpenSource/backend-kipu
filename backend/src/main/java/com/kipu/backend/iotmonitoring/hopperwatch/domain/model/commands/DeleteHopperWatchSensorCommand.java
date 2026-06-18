package com.kipu.backend.iotmonitoring.hopperwatch.domain.model.commands;

public record DeleteHopperWatchSensorCommand(Long id) {
    public DeleteHopperWatchSensorCommand {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID is required");
    }
}
