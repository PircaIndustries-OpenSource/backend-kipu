package com.kipu.backend.iotmonitoring.geolocalization.application.queryservices;

import com.kipu.backend.iotmonitoring.geolocalization.domain.model.aggregates.GeolocalizationSensor;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.queries.GetAllGeolocalizationSensorsQuery;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.queries.GetGeolocalizationSensorByIdQuery;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.queries.GetGeolocalizationSensorBySensorIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for Geolocalization bounded-context read queries.
 */
public interface GeolocalizationSensorQueryService {

    /**
     * Handle Get Geolocalization Sensor By ID Query
     *
     * @param query The {@link GetGeolocalizationSensorByIdQuery} Query
     * @return A {@link GeolocalizationSensor} instance if the query is valid, otherwise empty
     */
    Optional<GeolocalizationSensor> handle(GetGeolocalizationSensorByIdQuery query);

    /**
     * Handle Get Geolocalization Sensor By Sensor ID Query
     *
     * @param query The {@link GetGeolocalizationSensorBySensorIdQuery} Query
     * @return A {@link GeolocalizationSensor} instance if the query is valid, otherwise empty
     */
    Optional<GeolocalizationSensor> handle(GetGeolocalizationSensorBySensorIdQuery query);

    /**
     * Handle Get All Geolocalization Sensors Query
     *
     * @param query The {@link GetAllGeolocalizationSensorsQuery} Query
     * @return A list of {@link GeolocalizationSensor} instances
     */
    List<GeolocalizationSensor> handle(GetAllGeolocalizationSensorsQuery query);
}