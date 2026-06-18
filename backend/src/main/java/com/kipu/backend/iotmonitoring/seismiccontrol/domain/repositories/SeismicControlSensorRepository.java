package com.kipu.backend.iotmonitoring.seismiccontrol.domain.repositories;

import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.aggregates.SeismicControlSensor;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.valueobjects.SensorId;

import java.util.List;
import java.util.Optional;

/**
 * Seismic control repository port.
 *
 * <p>Defines the pure domain contract for persisting and retrieving {@link SeismicControlSensor} aggregates.
 * This interface is completely decoupled from concrete infrastructure frameworks like Spring Data JPA.</p>
 */
public interface SeismicControlSensorRepository {

    Optional<SeismicControlSensor> findById(Long id);

    Optional<SeismicControlSensor> findBySensorId(SensorId sensorId);

    List<SeismicControlSensor> findAll();

    SeismicControlSensor save(SeismicControlSensor seismicControl);

    boolean existsBySensorId(SensorId sensorId);

    List<SeismicControlSensor> findAllByProjectId(String projectId);
}