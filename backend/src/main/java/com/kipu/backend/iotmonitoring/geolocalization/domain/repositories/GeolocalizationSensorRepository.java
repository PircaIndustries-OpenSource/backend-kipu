package com.kipu.backend.iotmonitoring.geolocalization.domain.repositories;

import com.kipu.backend.iotmonitoring.geolocalization.domain.model.aggregates.GeolocalizationSensor;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.valueobjects.SensorId;

import java.util.List;
import java.util.Optional;

/**
 * GeolocalizationSensor repository port.
 */
public interface GeolocalizationSensorRepository {

    Optional<GeolocalizationSensor> findById(Long id);

    Optional<GeolocalizationSensor> findBySensorId(SensorId sensorId);

    List<GeolocalizationSensor> findAll();

    List<GeolocalizationSensor> findAllByProjectId(String projectId);

    GeolocalizationSensor save(GeolocalizationSensor geolocalizationAsset);

    boolean existsBySensorId(SensorId sensorId);

    void deleteById(Long id);

    boolean existsById(Long id);
}