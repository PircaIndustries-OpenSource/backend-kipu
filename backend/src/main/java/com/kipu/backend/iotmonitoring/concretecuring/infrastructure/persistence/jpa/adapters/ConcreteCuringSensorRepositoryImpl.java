package com.kipu.backend.iotmonitoring.concretecuring.infrastructure.persistence.jpa.adapters;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.aggregates.ConcreteCuringSensor;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.SensorId;
import com.kipu.backend.iotmonitoring.concretecuring.domain.repositories.ConcreteCuringSensorRepository;
import com.kipu.backend.iotmonitoring.concretecuring.infrastructure.persistence.jpa.assemblers.ConcreteCuringSensorPersistenceAssembler;
import com.kipu.backend.iotmonitoring.concretecuring.infrastructure.persistence.jpa.repositories.ConcreteCuringSensorPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the domain concrete curing repository port with Spring Data JPA.
 *
 * <p>Also acts as the event-publishing boundary: after a brand-new {@link ConcreteCuringSensor} is
 * persisted (and the JPA-assigned id is therefore available), its associated domain events
 * are dispatched via Spring's {@link ApplicationEventPublisher}.</p>
 */
@Repository
public class ConcreteCuringSensorRepositoryImpl implements ConcreteCuringSensorRepository {

    private final ConcreteCuringSensorPersistenceRepository concreteCuringPersistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ConcreteCuringSensorRepositoryImpl(
            ConcreteCuringSensorPersistenceRepository concreteCuringPersistenceRepository,
            ApplicationEventPublisher eventPublisher) {
        this.concreteCuringPersistenceRepository = concreteCuringPersistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<ConcreteCuringSensor> findById(Long id) {
        return concreteCuringPersistenceRepository.findById(id)
                .map(ConcreteCuringSensorPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<ConcreteCuringSensor> findBySensorId(SensorId sensorId) {
        return concreteCuringPersistenceRepository.findBySensorId(sensorId)
                .map(ConcreteCuringSensorPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<ConcreteCuringSensor> findAll() {
        return concreteCuringPersistenceRepository.findAll().stream()
                .map(ConcreteCuringSensorPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public ConcreteCuringSensor save(ConcreteCuringSensor concreteCuring) {
        // 1. Verificar si el agregado es nuevo antes de guardar (para saber si disparamos eventos)
        boolean isNew = concreteCuring.getId() == null;

        // 2. Mapear a entidad de infraestructura y persistir en MySQL
        var savedEntity = concreteCuringPersistenceRepository.save(
                ConcreteCuringSensorPersistenceAssembler.toPersistenceFromDomain(concreteCuring));

        // 3. Re-mapear la entidad persistida (que ya incluye el ID autogenerado) de vuelta a Dominio
        var savedConcreteCuring = ConcreteCuringSensorPersistenceAssembler.toDomainFromPersistence(savedEntity);

        // 4. Gestión y publicación de eventos de dominio si el registro es nuevo
        if (isNew) {
            // Invoca el método del agregado que registra el evento interno (ej. ConcreteCuringStartedEvent)
            savedConcreteCuring.onCreated();

            // Publica todos los eventos acumulados en el agregado a través del bus de Spring
            savedConcreteCuring.domainEvents().forEach(eventPublisher::publishEvent);

            // Limpia la lista de eventos para evitar duplicidad de envíos
            savedConcreteCuring.clearDomainEvents();
        }

        return savedConcreteCuring;
    }

    @Override
    public boolean existsBySensorId(SensorId sensorId) {
        return concreteCuringPersistenceRepository.countBySensorId(sensorId) > 0;
    }

    @Override
    public List<ConcreteCuringSensor> findAllByProjectId(String projectId){
        return concreteCuringPersistenceRepository.findAllByProjectId(projectId).stream()
                .map(ConcreteCuringSensorPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public void deleteById(Long id) {concreteCuringPersistenceRepository.deleteById(id);}

    @Override
    public boolean existsById(Long id) {
        return concreteCuringPersistenceRepository.existsById(id);
    }
}