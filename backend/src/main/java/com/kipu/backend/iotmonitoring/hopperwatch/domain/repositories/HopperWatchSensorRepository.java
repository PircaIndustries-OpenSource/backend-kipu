package com.kipu.backend.iotmonitoring.hopperwatch.domain.repositories;

import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.aggregates.HopperWatchSensor;

import java.util.List;
import java.util.Optional;

/**
 * HopperWatchSensor repository port.
 */
public interface HopperWatchSensorRepository {

    Optional<HopperWatchSensor> findById(Long id);

    Optional<HopperWatchSensor> findBySensorId(String sensorId);

    List<HopperWatchSensor> findAll();

    HopperWatchSensor save(HopperWatchSensor hopperWatch);

    boolean existsBySensorId(String sensorId);

    // Tip extra muy útil para Kipu: Buscar todas las tolvas de un mismo proyecto
    List<HopperWatchSensor> findAllByProjectId(String projectId);
}