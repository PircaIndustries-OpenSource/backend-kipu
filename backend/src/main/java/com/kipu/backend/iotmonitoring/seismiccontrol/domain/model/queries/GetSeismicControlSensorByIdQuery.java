package com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.queries;

/**
 * Query record to retrieve a seismic control monitor by its unique database identifier.
 * <p>Used to locate a specific telemetry record via its autoincremental primary key.</p>
 *
 * @param seismicControlId The technical database identifier of the aggregate root.
 */
public record GetSeismicControlSensorByIdQuery(Long seismicControlId) {
}