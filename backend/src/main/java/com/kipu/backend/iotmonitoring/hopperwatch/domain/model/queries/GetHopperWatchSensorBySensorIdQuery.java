package com.kipu.backend.iotmonitoring.hopperwatch.domain.model.queries;

import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.valueobjects.SensorId;

/**
 * Query record to retrieve a seismic control monitor by its hardware Sensor ID.
 * <p>Used to find specific device logs using the domain-specific
 * unique identifier of the physical IoT hardware.</p>
 *
 * @param sensorId The strongly-typed {@link SensorId} value object of the device.
 */
public record GetHopperWatchSensorBySensorIdQuery(SensorId sensorId) {
}