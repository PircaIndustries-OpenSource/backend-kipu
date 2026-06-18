package com.kipu.backend.iotmonitoring.concretecuring.domain.repositories;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.aggregates.ConcreteCuringSensor;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.SensorId;

import java.util.List;
import java.util.Optional;

/**
 * Concrete curing sensor repository port
 */
public interface ConcreteCuringSensorRepository {
    Optional<ConcreteCuringSensor> findById(Long id);

    Optional<ConcreteCuringSensor> findBySensorId(SensorId sensorId);

    List<ConcreteCuringSensor> findAll();

    ConcreteCuringSensor save(ConcreteCuringSensor concreteCuringSensor);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsBySensorId(SensorId sensorId);

    List<ConcreteCuringSensor> findAllByProjectId(String projectId);
}
