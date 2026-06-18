package com.kipu.backend.iotmonitoring.seismiccontrol.application.queryservices;

import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.aggregates.SeismicControlSensor;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.queries.GetAllSeismicControlSensorsQuery;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.queries.GetSeismicControlSensorByIdQuery;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.queries.GetSeismicControlSensorBySensorIdQuery;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.queries.GetSeismicControlSensorsByProjectIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for Seismic Control bounded-context read queries.
 * <p>Defines the read-only boundary boundary utilizing specialized query criteria
 * objects for optimal data retrieval segregation (CQRS).</p>
 */
public interface SeismicControlSensorQueryService {

    /**
     * Handles the retrieval of a seismic control sensor node by its database internal ID.
     *
     * @param query The {@link GetSeismicControlSensorByIdQuery} query.
     * @return An {@link Optional} containing the {@link SeismicControlSensor} if found, otherwise empty.
     */
    Optional<SeismicControlSensor> handle(GetSeismicControlSensorByIdQuery query);

    /**
     * Handles the retrieval of a seismic control sensor node by its physical hardware code.
     *
     * @param query The {@link GetSeismicControlSensorBySensorIdQuery} query.
     * @return An {@link Optional} containing the {@link SeismicControlSensor} if found, otherwise empty.
     */
    Optional<SeismicControlSensor> handle(GetSeismicControlSensorBySensorIdQuery query);

    /**
     * Handles the retrieval of all seismic control sensor nodes registered in the platform.
     *
     * @param query The {@link GetAllSeismicControlSensorsQuery} query.
     * @return A {@link List} of active {@link SeismicControlSensor} aggregate roots.
     */
    List<SeismicControlSensor> handle(GetAllSeismicControlSensorsQuery query);

    /**
     * Handle Get Hopper Watches By Project ID Query
     * @param query The {@link GetSeismicControlSensorsByProjectIdQuery} Query
     * @return A list of {@link SeismicControlSensor} instances associated with the project
     */
    List<SeismicControlSensor> handle(GetSeismicControlSensorsByProjectIdQuery query);
}