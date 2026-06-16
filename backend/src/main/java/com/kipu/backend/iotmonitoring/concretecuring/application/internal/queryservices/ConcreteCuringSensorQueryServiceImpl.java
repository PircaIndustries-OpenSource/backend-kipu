package com.kipu.backend.iotmonitoring.concretecuring.application.internal.queryservices;

import com.kipu.backend.iotmonitoring.concretecuring.application.queryservices.ConcreteCuringSensorQueryService;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.aggregates.ConcreteCuringSensor;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.queries.GetAllConcreteCuringSensorQueries;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.queries.GetConcreteCuringSensorByIdQuery;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.queries.GetConcreteCuringSensorBySensorIdQuery;
import com.kipu.backend.iotmonitoring.concretecuring.domain.repositories.ConcreteCuringSensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that resolves Concrete Curing bounded-context read queries.
 */
@Service
public class ConcreteCuringSensorQueryServiceImpl implements ConcreteCuringSensorQueryService {
    private final ConcreteCuringSensorRepository concreteCuringSensorRepository;

    /**
     * Creates the query service with the concrete curing repository dependency.
     *
     * @param concreteCuringSensorRepository concrete curing repository port
     */
    public ConcreteCuringSensorQueryServiceImpl(ConcreteCuringSensorRepository concreteCuringSensorRepository) {
        this.concreteCuringSensorRepository = concreteCuringSensorRepository;
    }

    // inherited javadoc
    @Override
    public Optional<ConcreteCuringSensor> handle(GetConcreteCuringSensorByIdQuery query) {
        return concreteCuringSensorRepository.findById(query.concreteCuringSensorId());
    }

    // inherited javadoc
    @Override
    public Optional<ConcreteCuringSensor> handle(GetConcreteCuringSensorBySensorIdQuery query) {
        return concreteCuringSensorRepository.findBySensorId(query.sensorId());
    }

    // inherited javadoc
    @Override
    public List<ConcreteCuringSensor> handle(GetAllConcreteCuringSensorQueries query) {
        return concreteCuringSensorRepository.findAll();
    }
}