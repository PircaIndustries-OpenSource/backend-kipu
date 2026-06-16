package com.kipu.backend.iotmonitoring.geolocalization.infrastructure.persistence.jpa.adapters;

import com.kipu.backend.iotmonitoring.geolocalization.domain.model.aggregates.GeolocalizationSensor;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.events.GeolocalizationSensorCreatedEvent;
import com.kipu.backend.iotmonitoring.geolocalization.domain.repositories.GeolocalizationSensorRepository;
import com.kipu.backend.iotmonitoring.geolocalization.infrastructure.persistence.jpa.assemblers.GeolocalizationSensorPersistenceAssembler;
import com.kipu.backend.iotmonitoring.geolocalization.infrastructure.persistence.jpa.repositories.GeolocalizationSensorPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the domain geolocalization sensor repository port with Spring Data JPA.
 *
 * <p>Also acts as the event-publishing boundary: after a brand-new {@link GeolocalizationSensor} is
 * persisted (and the JPA-assigned id is therefore available), a {@link GeolocalizationSensorCreatedEvent}
 * is dispatched via Spring's {@link ApplicationEventPublisher}.</p>
 */
@Repository
public class GeolocalizationSensorRepositoryImpl implements GeolocalizationSensorRepository {

    private final GeolocalizationSensorPersistenceRepository geolocalizationSensorPersistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public GeolocalizationSensorRepositoryImpl(
            GeolocalizationSensorPersistenceRepository geolocalizationSensorPersistenceRepository,
            ApplicationEventPublisher eventPublisher) {
        this.geolocalizationSensorPersistenceRepository = geolocalizationSensorPersistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<GeolocalizationSensor> findById(Long id) {
        return geolocalizationSensorPersistenceRepository.findById(id)
                .map(GeolocalizationSensorPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<GeolocalizationSensor> findBySensorId(String sensorId) {
        return geolocalizationSensorPersistenceRepository.findBySensorId(sensorId)
                .map(GeolocalizationSensorPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<GeolocalizationSensor> findAll() {
        return geolocalizationSensorPersistenceRepository.findAll().stream()
                .map(GeolocalizationSensorPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<GeolocalizationSensor> findAllByProjectId(String projectId) {
        return geolocalizationSensorPersistenceRepository.findAllByProjectId(projectId).stream()
                .map(GeolocalizationSensorPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public GeolocalizationSensor save(GeolocalizationSensor geolocalizationSensor) {
        // 1. Verificamos si es un registro nuevo antes de que JPA le asigne un ID físico
        boolean isNew = geolocalizationSensor.getId() == null;

        // 2. Traducimos a Entidad, persistimos en MySQL y rehidratamos a un Agregado con su ID generado
        var savedEntity = geolocalizationSensorPersistenceRepository.save(
                GeolocalizationSensorPersistenceAssembler.toPersistenceFromDomain(geolocalizationSensor));
        var savedSensor = GeolocalizationSensorPersistenceAssembler.toDomainFromPersistence(savedEntity);

        // 3. Si era nuevo, registramos la acción en el ciclo de vida del dominio y disparamos los eventos
        if (isNew) {
            savedSensor.onCreated(); // Prepara el GeolocalizationSensorCreatedEvent internamente
            savedSensor.domainEvents().forEach(eventPublisher::publishEvent); // Despacha los eventos al bus de Spring
            savedSensor.clearDomainEvents(); // Limpia la lista del agregado para evitar duplicados
        }

        return savedSensor;
    }

    @Override
    public boolean existsBySensorId(String sensorId) {
        // Ejecuta la consulta JPQL optimizada que definimos en el paso anterior
        return geolocalizationSensorPersistenceRepository.countBySensorId(sensorId) > 0;
    }
}