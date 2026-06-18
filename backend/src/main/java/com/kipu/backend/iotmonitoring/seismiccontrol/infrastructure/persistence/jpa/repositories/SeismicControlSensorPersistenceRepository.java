package com.kipu.backend.iotmonitoring.seismiccontrol.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.valueobjects.SensorId;
import com.kipu.backend.iotmonitoring.seismiccontrol.infrastructure.persistence.jpa.entities.SeismicControlSensorPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data repository for seismic control sensor persistence entities.
 * <p>Provides direct database access operations using Spring Data JPA
 * and JPQL queries optimized for hardware monitoring tracking.</p>
 */
@Repository
public interface SeismicControlSensorPersistenceRepository extends JpaRepository<SeismicControlSensorPersistenceEntity, Long> {

    @Query("select sensor from SeismicControlSensorPersistenceEntity sensor where sensor.sensorId = :sensorId")
    Optional<SeismicControlSensorPersistenceEntity> findBySensorId(@Param("sensorId") SensorId sensorId);

    @Query("select count(sensor) from SeismicControlSensorPersistenceEntity sensor where sensor.sensorId = :sensorId")
    long countBySensorId(@Param("sensorId") SensorId sensorId);

    @Query("select sensor from SeismicControlSensorPersistenceEntity sensor where sensor.projectId = :projectId")
    List<SeismicControlSensorPersistenceEntity> findAllByProjectId(@Param("projectId") String projectId);

}