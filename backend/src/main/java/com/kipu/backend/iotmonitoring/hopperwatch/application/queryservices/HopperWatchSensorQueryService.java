package com.kipu.backend.iotmonitoring.hopperwatch.application.queryservices;

import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.aggregates.HopperWatchSensor;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.queries.GetAllHopperWatchSensorsQuery;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.queries.GetHopperWatchSensorByIdQuery;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.queries.GetHopperWatchSensorBySensorIdQuery;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.queries.GetHopperWatchSensorsByProjectIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for Hopper Watch bounded-context read queries.
 */
public interface HopperWatchSensorQueryService {

    /**
     * Handle Get Hopper Watch By ID Query
     *
     * @param query The {@link GetHopperWatchSensorByIdQuery} Query
     * @return A {@link HopperWatchSensor} instance if the query is valid, otherwise empty
     */
    Optional<HopperWatchSensor> handle(GetHopperWatchSensorByIdQuery query);

    /**
     * Handle Get Hopper Watch By Sensor ID Query
     *
     * @param query The {@link GetHopperWatchSensorBySensorIdQuery} Query
     * @return A {@link HopperWatchSensor} instance if the query is valid, otherwise empty
     */
    Optional<HopperWatchSensor> handle(GetHopperWatchSensorBySensorIdQuery query);

    /**
     * Handle Get All Hopper Watches Query
     *
     * @param query The {@link GetAllHopperWatchSensorsQuery} Query
     * @return A list of {@link HopperWatchSensor} instances
     */
    List<HopperWatchSensor> handle(GetAllHopperWatchSensorsQuery query);

    /**
     * Handle Get Hopper Watches By Project ID Query
     *
     * @param query The {@link GetHopperWatchSensorsByProjectIdQuery} Query
     * @return A list of {@link HopperWatchSensor} instances associated with the project
     */
    List<HopperWatchSensor> handle(GetHopperWatchSensorsByProjectIdQuery query);
}