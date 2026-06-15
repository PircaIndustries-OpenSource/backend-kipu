package com.kipu.backend.iotmonitoring.concretecuring.domain.repositories;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.aggregates.ConcreteCuringSensor;

import java.util.List;
import java.util.Optional;

/**
 * Concrete curing sensor repository port
 */
public interface ConcreteCuringSensorRepository {
    Optional<ConcreteCuringSensor> findById(Long id);

    Optional<ConcreteCuringSensor> findBySensorId(String sensorId);

    List<ConcreteCuringSensor> findAll();

    ConcreteCuringSensor save(ConcreteCuringSensor concreteCuringSensor);

    boolean existsBySensorId(String sensorId);
}
