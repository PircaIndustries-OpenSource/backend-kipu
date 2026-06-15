package com.kipu.backend.iotmonitoring.concretecuring.domain.model.events;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.aggregates.ConcreteCuringSensor;

/**
 * Domain event published when a new {@link ConcreteCuringSensor} process is successfully created and persisted.
 *
 * <p>Other bounded contexts (e.g. {@code analytics} or {@code notifications}) can listen to this event
 * to react to concrete curing monitoring initialization without directly coupling to the {@code iotmonitoring}
 * application services.</p>
 *
 * @param concreteCuringId The identity assigned to the newly created concrete curing process.
 * @param projectId        The identifier of the associated project.
 * @param sensorId         The identifier of the physical IoT sensor.
 * @param state            The ordinal representation of the curing state.
 * @param location         The physical location within the construction site.
 * @param temperature      The initial temperature reading from the sensor.
 * @param unit             The measurement unit for temperature (e.g., "C", "F").
 * @param humidity         The initial relative humidity percentage.
 * @param limit            The safe temperature threshold limit before alerting.
 */
public record ConcreteCuringSensorCreatedEvent(
        Long concreteCuringId,
        String projectId,
        String sensorId,
        Integer state,
        String location,
        Double temperature,
        String unit,
        Integer humidity,
        Double limit
) {
    /**
     * Convenience factory that extracts all needed fields from a saved {@link ConcreteCuringSensor}.
     *
     * @param concreteCuringSensor the saved concrete curing aggregate (must already carry a non-null id)
     * @return a fully populated {@link ConcreteCuringSensorCreatedEvent}
     */
    public static ConcreteCuringSensorCreatedEvent from (ConcreteCuringSensor concreteCuringSensor) {
        return new ConcreteCuringSensorCreatedEvent(
                concreteCuringSensor.getId(),
                concreteCuringSensor.getProjectId(),
                concreteCuringSensor.getSensorId(),
                concreteCuringSensor.getState().ordinal(),
                concreteCuringSensor.getLocation(),
                concreteCuringSensor.getTemperatureReading(),
                concreteCuringSensor.getTemperatureUnit(),
                concreteCuringSensor.getHumidityPercentage(),
                concreteCuringSensor.getTemperatureLimit()
        );
    }
}
