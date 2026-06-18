package com.kipu.backend.iotmonitoring.seismiccontrol.infrastructure.persistence.jpa.adapters;

import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.aggregates.SeismicControlSensor;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.valueobjects.SensorId;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.repositories.SeismicControlSensorRepository;
import com.kipu.backend.iotmonitoring.seismiccontrol.infrastructure.persistence.jpa.assemblers.SeismicControlSensorPersistenceAssembler;
import com.kipu.backend.iotmonitoring.seismiccontrol.infrastructure.persistence.jpa.repositories.SeismicControlSensorPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the domain seismic control sensor repository port with Spring Data JPA.
 *
 * <p>Acts as the infrastructure implementation of the domain port and handles the event-publishing
 * boundary: after a brand-new {@link SeismicControlSensor} is successfully persisted, its accumulated
 * domain events are dispatched via Spring's {@link ApplicationEventPublisher}.</p>
 */
@Repository
public class SeismicControlSensorRepositoryImpl implements SeismicControlSensorRepository {

    private final SeismicControlSensorPersistenceRepository seismicControlSensorPersistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public SeismicControlSensorRepositoryImpl(
            SeismicControlSensorPersistenceRepository seismicControlSensorPersistenceRepository,
            ApplicationEventPublisher eventPublisher) {
        this.seismicControlSensorPersistenceRepository = seismicControlSensorPersistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<SeismicControlSensor> findById(Long id) {
        return seismicControlSensorPersistenceRepository.findById(id)
                .map(SeismicControlSensorPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<SeismicControlSensor> findBySensorId(SensorId sensorId) {
        return seismicControlSensorPersistenceRepository.findBySensorId(sensorId)
                .map(SeismicControlSensorPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<SeismicControlSensor> findAll() {
        return seismicControlSensorPersistenceRepository.findAll().stream()
                .map(SeismicControlSensorPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public SeismicControlSensor save(SeismicControlSensor seismicControlSensor) {
        boolean isNew = seismicControlSensor.getId() == null;

        // 1. Convertir agregado del dominio a entidad de persistencia y guardar en BD
        var persistenceEntity = SeismicControlSensorPersistenceAssembler.toPersistenceFromDomain(seismicControlSensor);
        var savedEntity = seismicControlSensorPersistenceRepository.save(persistenceEntity);

        // 2. Reconstruir el agregado con su ID técnico definitivo asignado por Azure SQL
        var savedSensor = SeismicControlSensorPersistenceAssembler.toDomainFromPersistence(savedEntity);

        // 3. Si es un registro nuevo, disparar el ciclo de vida del evento de dominio
        if (isNew) {
            savedSensor.onCreated(); // Registra internamente el SeismicControlSensorCreatedEvent
            savedSensor.domainEvents().forEach(eventPublisher::publishEvent);
            savedSensor.clearDomainEvents();
        }

        return savedSensor;
    }

    @Override
    public boolean existsBySensorId(SensorId sensorId) {
        return seismicControlSensorPersistenceRepository.countBySensorId(sensorId) > 0;
    }

    @Override
    public List<SeismicControlSensor> findAllByProjectId(String projectId) {
        return seismicControlSensorPersistenceRepository.findAllByProjectId(projectId).stream()
                .map(SeismicControlSensorPersistenceAssembler::toDomainFromPersistence).
                toList();
    }

    @Override
    public void deleteById(Long id) {
        seismicControlSensorPersistenceRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return seismicControlSensorPersistenceRepository.existsById(id);
    }
}