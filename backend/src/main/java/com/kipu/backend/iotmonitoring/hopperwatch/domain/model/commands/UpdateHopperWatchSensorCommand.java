package com.kipu.backend.iotmonitoring.hopperwatch.domain.model.commands;

public record UpdateHopperWatchSensorCommand(
        Long id,
        String sensorId,
        String name,
        String unit,
        Integer state,
        Integer lastLecture,
        Integer limit
) {
    public UpdateHopperWatchSensorCommand {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID is required");
        if (sensorId == null || sensorId.isBlank()) throw new IllegalArgumentException("Sensor ID is required");
    }
}
