package com.kipu.backend.iotmonitoring.geolocalization.application.acl;

import com.kipu.backend.iotmonitoring.geolocalization.application.commandservices.GeolocalizationSensorCommandService;
import com.kipu.backend.iotmonitoring.geolocalization.application.queryservices.GeolocalizationSensorQueryService;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.commands.CreateGeolocalizationSensorCommand;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.queries.GetGeolocalizationSensorBySensorIdQuery;
import com.kipu.backend.iotmonitoring.geolocalization.interfaces.acl.GeolocalizationSensorContextFacade;
import org.springframework.stereotype.Service;

/**
 * Application-layer implementation of the Geolocalization ACL facade.
 *
 * <p>Provides a simplified integration surface for other bounded contexts that need geolocalization
 * operations without coupling to Geolocalization internal models.</p>
 */
@Service
public class GeolocalizationSensorContextFacadeImpl implements GeolocalizationSensorContextFacade {

    private final GeolocalizationSensorCommandService geolocalizationSensorCommandService;
    private final GeolocalizationSensorQueryService geolocalizationSensorQueryService;

    /**
     * Constructor.
     *
     * @param geolocalizationSensorCommandService geolocalization command service port
     * @param geolocalizationSensorQueryService   geolocalization query service port
     */
    public GeolocalizationSensorContextFacadeImpl(
            GeolocalizationSensorCommandService geolocalizationSensorCommandService,
            GeolocalizationSensorQueryService geolocalizationSensorQueryService) {
        this.geolocalizationSensorCommandService = geolocalizationSensorCommandService;
        this.geolocalizationSensorQueryService = geolocalizationSensorQueryService;
    }

    /**
     * Creates a geolocalization sensor through the Geolocalization application command service.
     *
     * @return created geolocalization sensor identifier, or {@code 0L} when creation fails
     */
    @Override
    public Long createGeolocalizationSensor(
            String projectId,
            String sensorId,
            Integer numberId,
            String name,
            Integer state,
            double longitude,
            double latitude) {

        var createGeolocalizationSensorCommand = new CreateGeolocalizationSensorCommand(
                projectId,
                sensorId,
                numberId,
                name,
                state,
                longitude,
                latitude
        );

        var result = geolocalizationSensorCommandService.handle(createGeolocalizationSensorCommand);

        // Transforma el Result funcional a Optional; si es exitoso extrae el ID, si falló devuelve 0L de manera tolerante a fallos
        return result.toOptional()
                .map(sensor -> sensor.getId())
                .orElse(0L);
    }

    /**
     * Fetches a geolocalization sensor identifier by its sensor ID through the Geolocalization query service.
     *
     * @param sensorId The physical IoT sensor identifier.
     * @return geolocalization sensor identifier, or {@code 0L} when no sensor is found
     */
    @Override
    public Long fetchGeolocalizationSensorIdBySensorId(String sensorId) {
        // Al ser un String primitivo directo en Kipu, no instanciamos ningún Value Object complejo aquí
        var getGeolocalizationSensorBySensorIdQuery = new GetGeolocalizationSensorBySensorIdQuery(sensorId);

        var sensor = geolocalizationSensorQueryService.handle(getGeolocalizationSensorBySensorIdQuery);

        return sensor.isEmpty() ? 0L : sensor.get().getId();
    }
}