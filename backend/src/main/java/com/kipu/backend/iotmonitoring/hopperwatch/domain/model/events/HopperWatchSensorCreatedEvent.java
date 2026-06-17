package com.kipu.backend.iotmonitoring.hopperwatch.domain.model.events;

import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.aggregates.HopperWatchSensor;

/**
 * Domain event published when a new {@link HopperWatchSensor} monitor is successfully created and persisted.
 *
 * <p>Other bounded contexts (e.g., {@code analytics} or {@code notifications}) can listen to this event
 * to react to new hardware registrations without directly coupling to the {@code hopperwatch}
 * application services.</p>
 *
 * @param hopperWatchId The identity assigned to the newly created hopper watch monitor.
 * @param projectId     Associated project identifier code.
 * @param sensorId      Physical IoT sensor identifier.
 * @param name          Name or type of material inside the hopper.
 * @param unit          Measurement unit used by the sensor.
 * @param state         Current state numerical representation.
 * @param lastLecture   Value of the last lecture recorded by the sensor.
 * @param limit         Safety or capacity threshold limit for the material.
 */
public record HopperWatchSensorCreatedEvent(
        Long hopperWatchId,
        String projectId,
        String sensorId,
        String name,
        String unit,
        Integer state,
        Integer lastLecture,
        Integer limit) {

    /**
     * Convenience factory that extracts all needed fields from a saved {@link HopperWatchSensor}.
     *
     * @param hopperWatch the saved hopper watch aggregate (must already carry a non-null id)
     * @return a fully populated {@link HopperWatchSensorCreatedEvent}
     */
    public static HopperWatchSensorCreatedEvent from(HopperWatchSensor hopperWatch) {
        return new HopperWatchSensorCreatedEvent(
                hopperWatch.getId(),
                hopperWatch.getProjectId(),
                hopperWatch.getSensorId(),
                hopperWatch.getName(),
                hopperWatch.getUnit(),
                hopperWatch.getStateValue(),
                hopperWatch.getLastLecture(),
                hopperWatch.getLimit()
        );
    }
}