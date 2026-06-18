package com.kipu.backend.iotmonitoring.geolocalization.domain.model.commands;

public record DeleteGeolocalizationSensorCommand(Long id) {
    public DeleteGeolocalizationSensorCommand {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID is required");
    }
}
