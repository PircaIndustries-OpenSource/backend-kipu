package com.kipu.backend.iotmonitoring.geolocalization.application.internal.queryservices;

import com.kipu.backend.iotmonitoring.geolocalization.application.queryservices.GeolocalizationSensorQueryService;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.aggregates.GeolocalizationSensor;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.queries.GetAllGeolocalizationSensorsQuery;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.queries.GetGeolocalizationSensorByIdQuery;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.queries.GetGeolocalizationSensorBySensorIdQuery;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.queries.GetGeolocalizationSensorsByProjectIdQuery;
import com.kipu.backend.iotmonitoring.geolocalization.domain.repositories.GeolocalizationSensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that resolves Geolocalization bounded-context read queries.
 */
@Service
public class GeolocalizationSensorQueryServiceImpl implements GeolocalizationSensorQueryService {
    private final GeolocalizationSensorRepository geolocalizationSensorRepository;

    /**
     * Creates the query service with the geolocalization sensor repository dependency.
     *
     * @param geolocalizationSensorRepository geolocalization sensor repository port
     */
    public GeolocalizationSensorQueryServiceImpl(GeolocalizationSensorRepository geolocalizationSensorRepository) {
        this.geolocalizationSensorRepository = geolocalizationSensorRepository;
    }

    // inherited javadoc
    @Override
    public Optional<GeolocalizationSensor> handle(GetGeolocalizationSensorByIdQuery query) {
        return geolocalizationSensorRepository.findById(query.geolocalizationSensorId());
    }

    // inherited javadoc
    @Override
    public Optional<GeolocalizationSensor> handle(GetGeolocalizationSensorBySensorIdQuery query) {
        // Ejecuta la búsqueda limpia utilizando el String primitivo del sensor IoT
        return geolocalizationSensorRepository.findBySensorId(query.sensorId());
    }

    // inherited javadoc
    @Override
    public List<GeolocalizationSensor> handle(GetAllGeolocalizationSensorsQuery query) {
        return geolocalizationSensorRepository.findAll();
    }

    // inherited javadoc
    @Override
    public List<GeolocalizationSensor> handle(GetGeolocalizationSensorsByProjectIdQuery query) {
        return geolocalizationSensorRepository.findAllByProjectId(query.projectId());
    }
}