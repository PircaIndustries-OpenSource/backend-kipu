package com.kipu.backend.iotmonitoring.geolocalization.domain.model.commands;

public record UpdateGeolocalizationSensorCommand(
        Long id,
        String sensorId,
        Integer numberId,
        String name,
        Integer state,
        Double longitude,
        Double latitude
) {
    public UpdateGeolocalizationSensorCommand {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID is required");
        if (sensorId == null || sensorId.isBlank()) throw new IllegalArgumentException("Sensor ID is required");
    }
}
