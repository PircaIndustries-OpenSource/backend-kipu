package com.kipu.backend.iotmonitoring.geolocalization.domain.model.queries;

import com.kipu.backend.iotmonitoring.geolocalization.domain.model.valueobjects.SensorId;

/**
 * Get Geolocalization Sensor By Sensor ID Query
 */
public record GetGeolocalizationSensorBySensorIdQuery(SensorId sensorId) {
}