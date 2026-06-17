package com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.events;

import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.aggregates.SeismicControlSensor;

/**
 * Integration event published by the {@code seismiccontrol} bounded context when a new
 * {@link SeismicControlSensor} has been successfully created and persisted.
 *
 * <p>This is the <em>published language</em> of the {@code seismiccontrol} context.
 * Other bounded contexts must listen to this event rather than to internal domain events
 * such as {@link com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.events.SeismicControlSensorCreatedEvent},
 * which is an internal concern of the {@code seismiccontrol} domain.</p>
 *
 * @param seismicControlSensorId The identity assigned to the newly created seismic sensor.
 * @param projectId              The associated architectural project identifier.
 * @param sensorId               The physical hardware identification code.
 * @param unit                   The measurement metric unit.
 * @param lastLecture            The last recorded seismic telemetry value.
 * @param limit                  The safety threshold numeric limit.
 * @param location               The physical installation location description.
 * @param timeLecture            The timestamp text of the measurement lecture.
 * @param state                  The status integer code of the sensor.
 */
public record SeismicControlSensorCreatedIntegrationEvent(
        Long seismicControlSensorId,
        String projectId,
        String sensorId,
        String unit,
        Double lastLecture,
        Double limit,
        String location,
        String timeLecture,
        Integer state) {

    /**
     * Convenience factory that extracts all fields from a saved {@link SeismicControlSensor}.
     *
     * @param seismicControlSensor the saved seismic sensor (must already carry a non-null id)
     * @return a fully populated {@link SeismicControlSensorCreatedIntegrationEvent}
     */
    public static SeismicControlSensorCreatedIntegrationEvent from(SeismicControlSensor seismicControlSensor) {
        return new SeismicControlSensorCreatedIntegrationEvent(
                seismicControlSensor.getId(),
                seismicControlSensor.getProjectId(),
                seismicControlSensor.getSensorId(),     // Extrae el String crudo
                seismicControlSensor.getUnit(),         // Extrae el String de la unidad
                seismicControlSensor.getLastLecture(),  // Extrae el Double de la telemetría
                seismicControlSensor.getLimit(),        // Extrae el Double del límite de seguridad
                seismicControlSensor.getLocation(),
                seismicControlSensor.getTimeLecture(),
                seismicControlSensor.getState()         // Extrae el Integer del enum (1, 2, 3) como quedó en tu agregado
        );
    }
}