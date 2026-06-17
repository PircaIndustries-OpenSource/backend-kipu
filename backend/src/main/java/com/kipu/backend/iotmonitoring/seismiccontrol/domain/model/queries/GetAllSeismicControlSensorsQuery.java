package com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.queries;

/**
 * Query record to retrieve all registered seismic control monitors.
 * <p>Used within the CQRS pattern to signal the query service
 * to fetch the complete collection of vibration telemetry nodes.</p>
 */
public record GetAllSeismicControlSensorsQuery() {
}