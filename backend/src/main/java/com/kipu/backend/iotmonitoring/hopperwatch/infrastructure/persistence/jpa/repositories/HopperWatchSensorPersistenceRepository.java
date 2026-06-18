package com.kipu.backend.iotmonitoring.hopperwatch.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.iotmonitoring.hopperwatch.infrastructure.persistence.jpa.entities.HopperWatchSensorPersistenceEntity;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.valueobjects.SensorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data repository for hopper watch persistence entities.
 */
@Repository
public interface HopperWatchSensorPersistenceRepository extends JpaRepository<HopperWatchSensorPersistenceEntity, Long> {

    @Query("select hopper from HopperWatchSensorPersistenceEntity hopper where hopper.sensorId = :sensorId")
    Optional<HopperWatchSensorPersistenceEntity> findBySensorId(@Param("sensorId") SensorId sensorId);

    @Query("select count(hopper) from HopperWatchSensorPersistenceEntity hopper where hopper.sensorId = :sensorId")
    long countBySensorId(@Param("sensorId") SensorId sensorId);

    // Consulta adicional necesaria para el método findAllByProjectId que definimos en el puerto
    @Query("select hopper from HopperWatchSensorPersistenceEntity hopper where hopper.projectId = :projectId")
    List<HopperWatchSensorPersistenceEntity> findAllByProjectId(@Param("projectId") String projectId);
}