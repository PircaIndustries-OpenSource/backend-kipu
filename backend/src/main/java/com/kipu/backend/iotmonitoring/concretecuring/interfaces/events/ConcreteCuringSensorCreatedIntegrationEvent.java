package com.kipu.backend.iotmonitoring.concretecuring.interfaces.events;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.aggregates.ConcreteCuringSensor;

/**
 * Integration event published by the {@code concrete-curing} bounded context when a new
 * {@link ConcreteCuringSensor} process has been successfully created and persisted.
 *
 * <p>This is the <em>published language</em> of the {@code concrete-curing} context.
 * Other bounded contexts (e.g. {@code project-management}, {@code quality-assurance}) must listen to this event rather
 * than to internal domain events, which are an internal concern of the curing domain.</p>
 *
 * @param concreteCuringId   The identity assigned to the newly created curing process.
 * @param projectId          The identifier of the associated construction project.
 * @param sensorId           The physical IoT hardware sensor identifier.
 * @param state              The current operational state of the curing process (e.g., IN_PROGRESS).
 * @param location           The specific location description within the job site.
 * @param temperatureReading The initial temperature numeric reading.
 * @param temperatureUnit    The unit of measurement used for temperature (e.g., CELSIUS).
 * @param humidityPercentage The initial humidity percentage level.
 * @param temperatureLimit   The maximum safe temperature threshold configured.
 */
public record ConcreteCuringSensorCreatedIntegrationEvent(
        Long concreteCuringId,
        String projectId,
        String sensorId,
        String state,
        String location,
        Double temperatureReading,
        String temperatureUnit,
        Integer humidityPercentage,
        Double temperatureLimit) {

    /**
     * Convenience factory that extracts all fields from a saved {@link ConcreteCuringSensor}.
     *
     * @param concreteCuringSensor the saved concrete curing aggregate (must already carry a non-null id)
     * @return a fully populated {@link ConcreteCuringSensorCreatedIntegrationEvent}
     */
    public static ConcreteCuringSensorCreatedIntegrationEvent from(ConcreteCuringSensor concreteCuringSensor) {
        var temperature = concreteCuringSensor.getTemperatureValue();
        var humidity = concreteCuringSensor.getHumidityValue();

        return new ConcreteCuringSensorCreatedIntegrationEvent(
                concreteCuringSensor.getId(),
                concreteCuringSensor.getProjectId(),
                concreteCuringSensor.getSensorId(),
                concreteCuringSensor.getState().name(), // Convertimos el Value Object/Enum a String para el exterior
                concreteCuringSensor.getLocation(),
                temperature.reading(),
                temperature.unit(),
                humidity.percentage(), // Extraemos el primitivo numérico del Value Object de Humedad
                concreteCuringSensor.getTemperatureLimit()
        );
    }
}