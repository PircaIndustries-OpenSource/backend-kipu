package com.kipu.backend.iotmonitoring.geolocalization.interfaces.events;

import com.kipu.backend.iotmonitoring.geolocalization.domain.model.aggregates.GeolocalizationSensor;

/**
 * Integration event published by the {@code geolocalization} bounded context when a new
 * {@link GeolocalizationSensor} has been successfully created and persisted.
 *
 * <p>This is the <em>published language</em> of the {@code geolocalization} context.
 * Other bounded contexts must listen to this event rather than to internal domain events such as
 * {@link com.kipu.backend.iotmonitoring.geolocalization.domain.model.events.GeolocalizationSensorCreatedEvent},
 * which is an internal concern of the {@code geolocalization} domain.</p>
 *
 * @param geolocalizationSensorId The identity assigned to the newly created geolocalization sensor.
 * @param projectId              The project identifier associated with the sensor.
 * @param sensorId               The physical IoT sensor identifier.
 * @param numberId               The internal sequential number assigned to the sensor.
 * @param name                   The name assigned to the sensor.
 * @param state                  The textual representation of the sensor's current state.
 * @param longitude              The longitude coordinate component.
 * @param latitude               The latitude coordinate component.
 */
public record GeolocalizationSensorCreatedIntegrationEvent(
        Long geolocalizationSensorId,
        String projectId,
        String sensorId,
        Integer numberId,
        String name,
        String state,
        double longitude,
        double latitude) {

    /**
     * Convenience factory that extracts all fields from a saved {@link GeolocalizationSensor}.
     *
     * @param sensor the saved geolocalization sensor (must already carry a non-null id)
     * @return a fully populated {@link GeolocalizationSensorCreatedIntegrationEvent}
     */
    public static GeolocalizationSensorCreatedIntegrationEvent from(GeolocalizationSensor sensor) {
        var coordinates = sensor.getCoordinatesValue();
        return new GeolocalizationSensorCreatedIntegrationEvent(
                sensor.getId(),
                sensor.getProjectId(),
                sensor.getSensorId(),
                sensor.getNumberId(),
                sensor.getName(),
                sensor.getState().name(), // Enviamos el nombre del Enum como String para un contrato agnóstico
                coordinates.longitude(),
                coordinates.latitude()
        );
    }
}