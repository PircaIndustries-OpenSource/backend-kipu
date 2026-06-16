package com.kipu.backend.iotmonitoring.geolocalization.domain.model.commands;

/**
 * Create Geolocalization Command
 */
public record CreateGeolocalizationSensorCommand(String projectId,
                                           String sensorId,
                                           Integer numberId,
                                           String name,
                                           Integer state,
                                           Double longitude,
                                           Double latitude) {
}