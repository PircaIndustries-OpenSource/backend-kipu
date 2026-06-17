package com.kipu.backend.iotmonitoring.seismiccontrol.application.internal.queryservices;

import com.kipu.backend.iotmonitoring.seismiccontrol.application.queryservices.SeismicControlSensorQueryService;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.aggregates.SeismicControlSensor;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.queries.GetAllSeismicControlSensorsQuery;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.queries.GetSeismicControlSensorByIdQuery;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.queries.GetSeismicControlSensorBySensorIdQuery;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.repositories.SeismicControlSensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Seismic Control Sensor Query Service Implementation.
 * <p>Application service that resolves Seismic Control bounded-context read queries
 * by interacting cleanly with the domain repository boundary.</p>
 */
@Service
public class SeismicControlSensorQueryServiceImpl implements SeismicControlSensorQueryService {

    private final SeismicControlSensorRepository seismicControlSensorRepository;

    /**
     * Creates the query service with the seismic control sensor repository dependency.
     *
     * @param seismicControlSensorRepository seismic control sensor repository port
     */
    public SeismicControlSensorQueryServiceImpl(SeismicControlSensorRepository seismicControlSensorRepository) {
        this.seismicControlSensorRepository = seismicControlSensorRepository;
    }

    // inherited javadoc
    @Override
    public Optional<SeismicControlSensor> handle(GetSeismicControlSensorByIdQuery query) {
        return seismicControlSensorRepository.findById(query.seismicControlId());
    }

    // inherited javadoc
    @Override
    public Optional<SeismicControlSensor> handle(GetSeismicControlSensorBySensorIdQuery query) {
        return seismicControlSensorRepository.findBySensorId(query.sensorId());
    }

    // inherited javadoc
    @Override
    public List<SeismicControlSensor> handle(GetAllSeismicControlSensorsQuery query) {
        return seismicControlSensorRepository.findAll();
    }
}