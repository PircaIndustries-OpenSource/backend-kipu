package com.kipu.backend.iotmonitoring.hopperwatch.application.internal.eventhandlers;

import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.events.HopperWatchSensorCreatedEvent;
import com.kipu.backend.iotmonitoring.hopperwatch.interfaces.events.HopperWatchSensorCreatedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for the {@link HopperWatchSensorCreatedEvent} domain event.
 *
 * <p>Translates the internal domain event into a {@link HopperWatchSensorCreatedIntegrationEvent}
 * and re-publishes it on the Spring event bus. This is the only place where a domain
 * event crosses the boundary between the domain layer and the published language of the
 * {@code hopperwatch} bounded context.</p>
 *
 * <p>Other bounded contexts must subscribe to {@link HopperWatchSensorCreatedIntegrationEvent}
 * (from {@code hopperwatch.interfaces.events}), never to the internal
 * {@link HopperWatchSensorCreatedEvent}.</p>
 */
@Service("iotmonitoringHopperWatchSensorCreatedEventHandler")
public class HopperWatchSensorCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Constructor.
     *
     * @param eventPublisher Spring application event publisher
     */
    public HopperWatchSensorCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Receives the internal {@link HopperWatchSensorCreatedEvent} and publishes the
     * corresponding {@link HopperWatchSensorCreatedIntegrationEvent} for cross-context consumers.
     *
     * @param event the internal domain event
     */
    @EventListener
    public void on(HopperWatchSensorCreatedEvent event) {
        eventPublisher.publishEvent(new HopperWatchSensorCreatedIntegrationEvent(
                event.hopperWatchId(),
                event.projectId(),
                event.sensorId(),
                event.name(),
                event.unit(),
                event.state(),
                event.lastLecture(),
                event.limit()
        ));
    }
}