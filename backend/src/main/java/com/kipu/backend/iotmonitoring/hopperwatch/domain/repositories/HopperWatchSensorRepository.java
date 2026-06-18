package com.kipu.backend.iotmonitoring.hopperwatch.domain.repositories;

import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.aggregates.HopperWatchSensor;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.valueobjects.SensorId;

import java.util.List;
import java.util.Optional;

/**
 * HopperWatchSensor repository port.
 */
public interface HopperWatchSensorRepository {

    Optional<HopperWatchSensor> findById(Long id);

    Optional<HopperWatchSensor> findBySensorId(SensorId sensorId);

    List<HopperWatchSensor> findAll();

    HopperWatchSensor save(HopperWatchSensor hopperWatch);

    boolean existsBySensorId(SensorId sensorId);

    List<HopperWatchSensor> findAllByProjectId(String projectId);
}