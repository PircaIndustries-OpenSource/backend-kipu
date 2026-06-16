package com.kipu.backend.iotmonitoring.concretecuring.application.internal.eventhandlers;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.events.ConcreteCuringSensorCreatedEvent;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.CuringSensorState;
import com.kipu.backend.iotmonitoring.concretecuring.interfaces.events.ConcreteCuringSensorCreatedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for the {@link ConcreteCuringSensorCreatedEvent} domain event.
 *
 * <p>Translates the internal domain event into a {@link ConcreteCuringSensorCreatedIntegrationEvent}
 * and re-publishes it on the Spring event bus. This is the only place where a domain
 * event crosses the boundary between the domain layer and the published language of the
 * {@code concrete-curing} bounded context.</p>
 *
 * <p>Other bounded contexts must subscribe to {@link ConcreteCuringSensorCreatedIntegrationEvent}
 * (from {@code concretecuring.interfaces.events}), never to the internal
 * {@link ConcreteCuringSensorCreatedEvent}.</p>
 */
@Service("concreteCuringConcreteCuringCreatedEventHandler")
public class ConcreteCuringSensorCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Constructor.
     *
     * @param eventPublisher Spring application event publisher
     */
    public ConcreteCuringSensorCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Receives the internal {@link ConcreteCuringSensorCreatedEvent} and publishes the
     * corresponding {@link ConcreteCuringSensorCreatedIntegrationEvent} for cross-context consumers.
     *
     * @param event the internal domain event
     */
    @EventListener
    public void on(ConcreteCuringSensorCreatedEvent event) {
        String stateText = CuringSensorState.values()[event.state()].name();
        eventPublisher.publishEvent(new ConcreteCuringSensorCreatedIntegrationEvent(
                event.concreteCuringId(),
                event.projectId(),
                event.sensorId(),
                stateText,
                event.location(),
                event.temperature(),
                event.unit(),
                event.humidity(),
                event.limit()
        ));
    }
}