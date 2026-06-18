package com.kipu.backend.iotmonitoring.concretecuring.application.queryservices;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.aggregates.ConcreteCuringSensor;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.queries.GetAllConcreteCuringSensorQueries;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.queries.GetConcreteCuringSensorByIdQuery;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.queries.GetConcreteCuringSensorBySensorIdQuery;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.queries.GetConcreteCuringSensorsByProjectIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for Concrete Curing bounded-context read queries.
 */
public interface ConcreteCuringSensorQueryService {

    /**
     * Handle Get Concrete Curing By ID Query
     *
     * @param query The {@link GetConcreteCuringSensorByIdQuery} Query
     * @return A {@link ConcreteCuringSensor} instance if the query is valid, otherwise empty
     */
    Optional<ConcreteCuringSensor> handle(GetConcreteCuringSensorByIdQuery query);

    /**
     * Handle Get Concrete Curing By Sensor ID Query
     *
     * @param query The {@link GetConcreteCuringSensorBySensorIdQuery} Query
     * @return A {@link ConcreteCuringSensor} instance if the query is valid, otherwise empty
     */
    Optional<ConcreteCuringSensor> handle(GetConcreteCuringSensorBySensorIdQuery query);

    /**
     * Handle Get All Concrete Curing Sensor Queries
     *
     * @param query The {@link GetAllConcreteCuringSensorQueries} Query
     * @return A list of {@link ConcreteCuringSensor} instances
     */
    List<ConcreteCuringSensor> handle(GetAllConcreteCuringSensorQueries query);

    List<ConcreteCuringSensor> handle(GetConcreteCuringSensorsByProjectIdQuery query);
}