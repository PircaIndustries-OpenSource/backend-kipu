package com.kipu.backend.iotmonitoring.concretecuring.domain.model.queries;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.SensorId;

/**
 * Get Concrete Curing Sensor By Sensor Identifier Query
 * <p>Used to find specific device logs using the domain-specific
 * unique identifier of the physical IoT hardware.</p>
 *
 * @param sensorId The strongly-typed {@link SensorId}
 */
public record GetConcreteCuringSensorBySensorIdQuery(SensorId sensorId) {
}
