package com.kipu.backend.iotmonitoring.seismiccontrol.application.internal.eventhandlers;

import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.events.SeismicControlSensorCreatedEvent;
import com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.events.SeismicControlSensorCreatedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for the {@link SeismicControlSensorCreatedEvent} domain event.
 *
 * <p>Translates the internal domain event into a {@link SeismicControlSensorCreatedIntegrationEvent}
 * and re-publishes it on the Spring event bus. This is the only point where a domain
 * event crosses the boundary between the internal domain layer and the published language of the
 * {@code seismiccontrol} bounded context.</p>
 *
 * <p>Other bounded contexts must subscribe to {@link SeismicControlSensorCreatedIntegrationEvent}
 * (from {@code seismiccontrol.interfaces.events}), never to the internal domain concerns.</p>
 */
@Service("seismicControlSensorCreatedEventHandler")
public class SeismicControlSensorCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Constructor for dependency injection.
     *
     * @param eventPublisher Spring application event publisher
     */
    public SeismicControlSensorCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Receives the internal {@link SeismicControlSensorCreatedEvent} and publishes the
     * corresponding {@link SeismicControlSensorCreatedIntegrationEvent} for cross-context consumers.
     *
     * @param event the internal domain event emitted by the aggregate
     */
    @EventListener
    public void on(SeismicControlSensorCreatedEvent event) {
        eventPublisher.publishEvent(new SeismicControlSensorCreatedIntegrationEvent(
                event.seismicControlId(),
                event.projectId(),
                event.sensorId(),
                event.unit(),
                event.lastLecture(),
                event.limit(),
                event.location(),
                event.timeLecture(),
                event.state()
        ));
    }
}