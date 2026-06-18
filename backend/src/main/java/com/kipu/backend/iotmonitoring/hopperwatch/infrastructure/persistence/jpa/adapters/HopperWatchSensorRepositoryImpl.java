package com.kipu.backend.iotmonitoring.hopperwatch.infrastructure.persistence.jpa.adapters;

import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.aggregates.HopperWatchSensor;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.valueobjects.SensorId;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.repositories.HopperWatchSensorRepository;
import com.kipu.backend.iotmonitoring.hopperwatch.infrastructure.persistence.jpa.assemblers.HopperWatchSensorPersistenceAssembler;
import com.kipu.backend.iotmonitoring.hopperwatch.infrastructure.persistence.jpa.repositories.HopperWatchSensorPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the domain hopper watch repository port with Spring Data JPA.
 *
 * <p>Also acts as the event-publishing boundary: after a brand-new {@link HopperWatchSensor} is
 * persisted (and the JPA-assigned id is therefore available), a {@link com.kipu.backend.iotmonitoring.hopperwatch.domain.model.events.HopperWatchSensorCreatedEvent}
 * is dispatched via Spring's {@link ApplicationEventPublisher}.</p>
 */
@Repository
public class HopperWatchSensorRepositoryImpl implements HopperWatchSensorRepository {

    private final HopperWatchSensorPersistenceRepository hopperWatchPersistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public HopperWatchSensorRepositoryImpl(
            HopperWatchSensorPersistenceRepository hopperWatchPersistenceRepository,
            ApplicationEventPublisher eventPublisher) {
        this.hopperWatchPersistenceRepository = hopperWatchPersistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<HopperWatchSensor> findById(Long id) {
        return hopperWatchPersistenceRepository.findById(id)
                .map(HopperWatchSensorPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<HopperWatchSensor> findBySensorId(SensorId sensorId) {
        return hopperWatchPersistenceRepository.findBySensorId(sensorId)
                .map(HopperWatchSensorPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<HopperWatchSensor> findAll() {
        return hopperWatchPersistenceRepository.findAll().stream()
                .map(HopperWatchSensorPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public HopperWatchSensor save(HopperWatchSensor hopperWatch) {
        boolean isNew = hopperWatch.getId() == null;

        // 1. Transformamos el agregado a entidad de persistencia y lo guardamos en la DB
        var savedEntity = hopperWatchPersistenceRepository.save(
                HopperWatchSensorPersistenceAssembler.toPersistenceFromDomain(hopperWatch)
        );

        // 2. Reconstruimos el agregado con su nuevo ID asignado por JPA
        var savedHopperWatchSensor = HopperWatchSensorPersistenceAssembler.toDomainFromPersistence(savedEntity);

        // 3. Si era una entidad nueva, disparamos los eventos de dominio acumulados
        if (isNew) {
            savedHopperWatchSensor.onCreated(); // Registra el HopperWatchSensorCreatedEvent
            savedHopperWatchSensor.domainEvents().forEach(eventPublisher::publishEvent);
            savedHopperWatchSensor.clearDomainEvents(); // Evita re-publicaciones accidentales
        }

        return savedHopperWatchSensor;
    }

    @Override
    public boolean existsBySensorId(SensorId sensorId) {
        return hopperWatchPersistenceRepository.countBySensorId(sensorId) > 0;
    }

    @Override
    public List<HopperWatchSensor> findAllByProjectId(String projectId) {
        return hopperWatchPersistenceRepository.findAllByProjectId(projectId).stream()
                .map(HopperWatchSensorPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        hopperWatchPersistenceRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return hopperWatchPersistenceRepository.existsById(id);
    }
}