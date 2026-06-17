package com.kipu.backend.iotmonitoring.seismiccontrol.application.acl;

import com.kipu.backend.iotmonitoring.seismiccontrol.application.commandservices.SeismicControlSensorCommandService;
import com.kipu.backend.iotmonitoring.seismiccontrol.application.queryservices.SeismicControlSensorQueryService;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.commands.CreateSeismicControlSensorCommand;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.queries.GetSeismicControlSensorBySensorIdQuery;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.valueobjects.SensorId;
import com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.acl.SeismicControlSensorsContextFacade;
import org.springframework.stereotype.Service;

/**
 * Application-layer implementation of the Seismic Control ACL facade.
 *
 * <p>Provides a simplified integration surface for other bounded contexts that need
 * seismic sensor operations without coupling to its internal models or domain patterns.</p>
 */
@Service
public class SeismicControlSensorsContextFacadeImpl implements SeismicControlSensorsContextFacade {

    private final SeismicControlSensorCommandService seismicControlSensorCommandService;
    private final SeismicControlSensorQueryService seismicControlSensorQueryService;

    public SeismicControlSensorsContextFacadeImpl(
            SeismicControlSensorCommandService seismicControlSensorCommandService,
            SeismicControlSensorQueryService seismicControlSensorQueryService) {
        this.seismicControlSensorCommandService = seismicControlSensorCommandService;
        this.seismicControlSensorQueryService = seismicControlSensorQueryService;
    }

    /**
     * Creates a seismic sensor through the application command service.
     *
     * @return created sensor identifier, or {@code 0L} when creation fails
     */
    @Override
    public Long createSeismicControlSensor(
            String projectId,
            String sensorId,
            String unit,
            Double lastLecture,
            Double limit,
            String location,
            String timeLecture,
            Integer state) {

        var createSeismicControlSensorCommand = new CreateSeismicControlSensorCommand(
                projectId,
                sensorId,
                unit,
                lastLecture,
                limit,
                location,
                timeLecture,
                state);

        var result = seismicControlSensorCommandService.handle(createSeismicControlSensorCommand);

        return result.toOptional()
                .map(sensor -> sensor.getId())
                .orElse(0L);
    }

    /**
     * Fetches a seismic sensor identifier by its hardware code through the query service.
     *
     * @param sensorId physical hardware identification code
     * @return seismic control sensor database identifier, or {@code 0L} when not found
     */
    @Override
    public Long fetchSeismicControlSensorIdBySensorId(String sensorId) {
        var getSeismicControlSensorBySensorIdQuery = new GetSeismicControlSensorBySensorIdQuery(new SensorId(sensorId));
        var sensor = seismicControlSensorQueryService.handle(getSeismicControlSensorBySensorIdQuery);

        return sensor.isEmpty() ? 0L : sensor.get().getId();
    }
}