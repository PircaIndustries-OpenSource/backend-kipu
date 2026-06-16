package com.kipu.backend.iotmonitoring.geolocalization.application.internal.eventhandlers;

import com.kipu.backend.iotmonitoring.geolocalization.domain.model.events.GeolocalizationSensorCreatedEvent;
import com.kipu.backend.iotmonitoring.geolocalization.interfaces.events.GeolocalizationSensorCreatedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for the {@link GeolocalizationSensorCreatedEvent} domain event.
 *
 * <p>Translates the internal domain event into a {@link GeolocalizationSensorCreatedIntegrationEvent}
 * and re-publishes it on the Spring event bus. This is the only place where a domain
 * event crosses the boundary between the domain layer and the published language of the
 * {@code geolocalization} bounded context.</p>
 *
 * <p>Other bounded contexts must subscribe to {@link GeolocalizationSensorCreatedIntegrationEvent}
 * (from {@code geolocalization.interfaces.events}), never to the internal
 * {@link GeolocalizationSensorCreatedEvent}.</p>
 */
@Service("geolocalizationGeolocalizationSensorCreatedEventHandler")
public class GeolocalizationSensorCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Constructor.
     *
     * @param eventPublisher Spring application event publisher
     */
    public GeolocalizationSensorCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Receives the internal {@link GeolocalizationSensorCreatedEvent} and publishes the
     * corresponding {@link GeolocalizationSensorCreatedIntegrationEvent} for cross-context consumers.
     *
     * @param event the internal domain event
     */
    @EventListener
    public void on(GeolocalizationSensorCreatedEvent event) {
        eventPublisher.publishEvent(new GeolocalizationSensorCreatedIntegrationEvent(
                event.geolocalizationAssetId(),
                event.projectId(),
                event.sensorId(),
                event.numberId(),
                event.name(),
                event.state(), // Mapea el valor de texto plano configurado en el evento de origen
                event.longitude(),
                event.latitude()));
    }
}