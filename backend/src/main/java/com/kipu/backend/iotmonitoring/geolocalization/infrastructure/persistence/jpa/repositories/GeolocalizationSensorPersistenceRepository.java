package com.kipu.backend.iotmonitoring.geolocalization.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.iotmonitoring.geolocalization.domain.model.valueobjects.SensorId;
import com.kipu.backend.iotmonitoring.geolocalization.infrastructure.persistence.jpa.entities.GeolocalizationSensorPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data repository for geolocalization sensor persistence entities.
 */
@Repository
public interface GeolocalizationSensorPersistenceRepository extends JpaRepository<GeolocalizationSensorPersistenceEntity, Long> {

    /**
     * Finds a geolocalization sensor by its physical IoT sensor identifier.
     * @param sensorId The physical IoT sensor id.
     * @return An {@link Optional} containing the found entity, or empty if none matches.
     */
    @Query("select sensor from GeolocalizationSensorPersistenceEntity sensor where sensor.sensorId = :sensorId")
    Optional<GeolocalizationSensorPersistenceEntity> findBySensorId(@Param("sensorId") SensorId sensorId);

    /**
     * Counts how many sensors use a specific sensor identifier.
     * <p>Used by the infrastructure adapter to implement the unique constraint validation.</p>
     * @param sensorId The physical IoT sensor id.
     * @return The count of sensors matching the provided sensor id.
     */
    @Query("select count(sensor) from GeolocalizationSensorPersistenceEntity sensor where sensor.sensorId = :sensorId")
    long countBySensorId(@Param("sensorId") SensorId sensorId);

    /**
     * Finds all geolocalization sensors associated with a specific project.
     * @param projectId The project identifier.
     * @return A {@link List} of matching geolocalization sensor entities.
     */
    @Query("select sensor from GeolocalizationSensorPersistenceEntity sensor where sensor.projectId = :projectId")
    List<GeolocalizationSensorPersistenceEntity> findAllByProjectId(@Param("projectId") String projectId);
}