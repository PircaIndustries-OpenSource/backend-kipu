package com.kipu.backend.iotmonitoring.hopperwatch.interfaces.events;

import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.aggregates.HopperWatchSensor;

/**
 * Integration event published by the {@code hopperwatch} bounded context when a new
 * {@link HopperWatchSensor} has been successfully created and persisted.
 *
 * <p>This is the <em>published language</em> of the {@code hopperwatch} context.
 * Other bounded contexts must listen to this event rather than to internal domain events such as
 * {@link com.kipu.backend.iotmonitoring.hopperwatch.domain.model.events.HopperWatchSensorCreatedEvent},
 * which is an internal concern of the {@code hopperwatch} domain.</p>
 *
 * @param hopperWatchId The identity assigned to the newly created hopper watch.
 * @param projectId     The project identity associated with this hopper watch.
 * @param sensorId      The unique hardware sensor identifier.
 * @param name          The name of the hopper watch monitor.
 * @param unit          The measurement unit (e.g., kg, m3).
 * @param state         The numerical business value representing the state.
 * @param lastLecture   The last recorded measurement value from telemetry.
 * @param safetyLimit   The defined safety threshold limit for alerts.
 */
public record HopperWatchSensorCreatedIntegrationEvent(
        Long hopperWatchId,
        String projectId,
        String sensorId,
        String name,
        String unit,
        Integer state,
        Integer lastLecture,
        Integer safetyLimit) {

    /**
     * Convenience factory that extracts all fields from a saved {@link HopperWatchSensor}.
     *
     * @param hopperWatch the saved hopper watch (must already carry a non-null id)
     * @return a fully populated {@link HopperWatchSensorCreatedIntegrationEvent}
     */
    public static HopperWatchSensorCreatedIntegrationEvent from(HopperWatchSensor hopperWatch) {
        var measurement = hopperWatch.getMeasurementValue();
        return new HopperWatchSensorCreatedIntegrationEvent(
                hopperWatch.getId(),
                hopperWatch.getProjectId(),
                hopperWatch.getSensorId(),
                hopperWatch.getName(),
                hopperWatch.getUnit(),
                hopperWatch.getState() != null ? hopperWatch.getState().getValue() : null,
                measurement != null ? measurement.lastLecture() : null,
                measurement != null ? measurement.safetyLimit() : null
        );
    }
}