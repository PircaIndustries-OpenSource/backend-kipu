package com.kipu.backend.iotmonitoring.geolocalization.domain.model.events;

import com.kipu.backend.iotmonitoring.geolocalization.domain.model.aggregates.GeolocalizationSensor;

/**
 * Domain event published when a new {@link GeolocalizationSensor} is successfully created and persisted.
 *
 * <p>Other bounded contexts (e.g. {@code constructionmanagement} or {@code realtimedashboard}) can listen to this event
 * to react to equipment geolocalization updates without directly coupling to the {@code geolocalization}
 * application services.</p>
 *
 * @param geolocalizationAssetId The identity assigned to the newly registered asset tracking.
 * @param projectId              The associated project identifier.
 * @param sensorId               The physical IoT sensor identifier (nullable).
 * @param numberId               The internal numerical equipment identifier (nullable).
 * @param name                   The equipment or station name.
 * @param state                  The integer code representing the operational state.
 * @param longitude              The longitude coordinate where the asset is located.
 * @param latitude               The latitude coordinate where the asset is located.
 */
public record GeolocalizationSensorCreatedEvent(
        Long geolocalizationAssetId,
        String projectId,
        String sensorId,
        Integer numberId,
        String name,
        Integer state,
        Double longitude,
        Double latitude) {

    /**
     * Convenience factory that extracts all needed fields from a saved {@link GeolocalizationSensor}.
     *
     * @param asset the saved geolocalization asset (must already carry a non-null id)
     * @return a fully populated {@link GeolocalizationSensorCreatedEvent}
     */
    public static GeolocalizationSensorCreatedEvent from(GeolocalizationSensor asset) {
        return new GeolocalizationSensorCreatedEvent(
                asset.getId(),
                asset.getProjectId(),
                asset.getSensorId(),
                asset.getNumberId(),
                asset.getName(),
                asset.getState().getValue(), // Convertimos el Enum de vuelta a su entero (0 o 1) para el evento
                asset.getLongitude(),         // Extraído directamente desde el Value Object Coordinates
                asset.getLatitude()          // Extraído directamente desde el Value Object Coordinates
        );
    }
}