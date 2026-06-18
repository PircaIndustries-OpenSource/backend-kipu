package com.kipu.backend.iotmonitoring.hopperwatch.application.acl;

import com.kipu.backend.iotmonitoring.hopperwatch.application.commandservices.HopperWatchSensorCommandService;
import com.kipu.backend.iotmonitoring.hopperwatch.application.queryservices.HopperWatchSensorQueryService;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.commands.CreateHopperWatchSensorCommand;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.queries.GetHopperWatchSensorBySensorIdQuery;
import com.kipu.backend.iotmonitoring.hopperwatch.interfaces.acl.HopperWatchSensorContextFacade;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.valueobjects.SensorId;
import org.springframework.stereotype.Service;

/**
 * Application-layer implementation of the Hopper Watch ACL facade.
 *
 * <p>Provides a simplified integration surface for other bounded contexts that need hopper watch
 * operations without coupling to Hopper Watch internal models.</p>
 */
@Service
public class HopperWatchSensorContextFacadeImpl implements HopperWatchSensorContextFacade {
    private final HopperWatchSensorCommandService hopperWatchCommandService;
    private final HopperWatchSensorQueryService hopperWatchQueryService;

    public HopperWatchSensorContextFacadeImpl(
            HopperWatchSensorCommandService hopperWatchCommandService,
            HopperWatchSensorQueryService hopperWatchQueryService) {
        this.hopperWatchCommandService = hopperWatchCommandService;
        this.hopperWatchQueryService = hopperWatchQueryService;
    }

    /**
     * Creates a hopper watch through the Hopper Watch application command service.
     *
     * @return created hopper watch identifier, or {@code 0L} when creation fails
     */
    @Override
    public Long createHopperWatchSensor(
            String projectId,
            String sensorId,
            String name,
            Integer state,
            String unit,
            Integer lastLecture,
            Integer safetyLimit) {

        var createHopperWatchSensorCommand = new CreateHopperWatchSensorCommand(
                projectId,
                sensorId,
                name,
                unit,
                state,
                lastLecture,
                safetyLimit);

        var result = hopperWatchCommandService.handle(createHopperWatchSensorCommand);

        // Desempaqueta el Result funcional: si es exitoso extrae el ID, de lo contrario retorna 0L
        return result.toOptional()
                .map(hopperWatch -> hopperWatch.getId())
                .orElse(0L);
    }

    /**
     * Fetches a hopper watch identifier by its hardware sensor ID through the Hopper Watch query service.
     *
     * @param sensorId hopper watch hardware sensor identifier
     * @return hopper watch identifier, or {@code 0L} when no hopper watch is found
     */
    @Override
    public Long fetchHopperWatchSensorIdBySensorId(String sensorId) {
        var getHopperWatchSensorBySensorIdQuery = new GetHopperWatchSensorBySensorIdQuery(new SensorId(sensorId));
        var hopperWatch = hopperWatchQueryService.handle(getHopperWatchSensorBySensorIdQuery);

        return hopperWatch.isEmpty() ? 0L : hopperWatch.get().getId();
    }
}