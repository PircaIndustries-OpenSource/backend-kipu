package com.kipu.backend.iotmonitoring.hopperwatch.application.internal.queryservices;

import com.kipu.backend.iotmonitoring.hopperwatch.application.queryservices.HopperWatchSensorQueryService;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.aggregates.HopperWatchSensor;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.queries.GetAllHopperWatchSensorsQuery;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.queries.GetHopperWatchSensorByIdQuery;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.queries.GetHopperWatchSensorBySensorIdQuery;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.queries.GetHopperWatchSensorsByProjectIdQuery;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.repositories.HopperWatchSensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that resolves Hopper Watch bounded-context read queries.
 */
@Service
public class HopperWatchSensorQueryServiceImpl implements HopperWatchSensorQueryService {
    private final HopperWatchSensorRepository hopperWatchRepository;

    /**
     * Creates the query service with the hopper watch repository dependency.
     *
     * @param hopperWatchRepository hopper watch repository port
     */
    public HopperWatchSensorQueryServiceImpl(HopperWatchSensorRepository hopperWatchRepository) {
        this.hopperWatchRepository = hopperWatchRepository;
    }

    @Override
    public Optional<HopperWatchSensor> handle(GetHopperWatchSensorByIdQuery query) {
        return hopperWatchRepository.findById(query.hopperWatchId());
    }

    @Override
    public Optional<HopperWatchSensor> handle(GetHopperWatchSensorBySensorIdQuery query) {
        return hopperWatchRepository.findBySensorId(query.sensorId());
    }

    @Override
    public List<HopperWatchSensor> handle(GetAllHopperWatchSensorsQuery query) {
        return hopperWatchRepository.findAll();
    }

    @Override
    public List<HopperWatchSensor> handle(GetHopperWatchSensorsByProjectIdQuery query) {
        return hopperWatchRepository.findAllByProjectId(query.projectId());
    }
}