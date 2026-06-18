package com.kipu.backend.iotmonitoring.concretecuring.domain.model.commands;

public record UpdateConcreteCuringSensorCommand(
        Long id,
        String sensorId,
        Integer state,
        String location,
        Double temperature,
        String unit,
        Integer humidity,
        Double limit
) {
    public UpdateConcreteCuringSensorCommand {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID is required");
        if (sensorId == null || sensorId.isBlank()) throw new IllegalArgumentException("Sensor ID is required");
    }
}
