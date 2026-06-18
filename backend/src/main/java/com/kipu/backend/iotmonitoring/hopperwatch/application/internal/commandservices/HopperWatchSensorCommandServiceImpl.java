package com.kipu.backend.iotmonitoring.hopperwatch.application.internal.commandservices;

import com.kipu.backend.iotmonitoring.hopperwatch.application.commandservices.HopperWatchSensorCommandService;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.aggregates.HopperWatchSensor;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.commands.CreateHopperWatchSensorCommand;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.valueobjects.SensorId;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.repositories.HopperWatchSensorRepository;
import com.kipu.backend.shared.application.result.ApplicationError;
import com.kipu.backend.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Hopper Watch Command Service Implementation
 */
@Service
public class HopperWatchSensorCommandServiceImpl implements HopperWatchSensorCommandService {
    private final HopperWatchSensorRepository hopperWatchRepository;

    /**
     * Constructor
     *
     * @param hopperWatchRepository The {@link HopperWatchSensorRepository} instance
     */
    public HopperWatchSensorCommandServiceImpl(HopperWatchSensorRepository hopperWatchRepository) {
        this.hopperWatchRepository = hopperWatchRepository;
    }

    @Override
    public Result<HopperWatchSensor, ApplicationError> handle(CreateHopperWatchSensorCommand command) {
        try {
            // Regla de negocio: Verificar que el hardware de telemetría (sensorId) no esté duplicado
            var sensorId = new SensorId(command.sensorId());
            if (hopperWatchRepository.existsBySensorId(sensorId)) {
                return Result.failure(ApplicationError.conflict(
                        "HopperWatchSensor",
                        "A hopper watch with sensor ID '%s' already exists".formatted(command.sensorId())));
            }

            // Instanciamos el Agregado Raíz pasando el Command al constructor de diseño táctico
            var hopperWatch = new HopperWatchSensor(command);

            // El repositorio se encarga del almacenamiento y de disparar el HopperWatchSensorCreatedEvent
            var savedHopperWatchSensor = hopperWatchRepository.save(hopperWatch);

            return Result.success(savedHopperWatchSensor);

        } catch (IllegalArgumentException e) {
            // Captura validaciones de formato/lógicas fallidas en los Value Objects del constructor
            return Result.failure(ApplicationError.validationError("HopperWatchSensor", e.getMessage()));
        } catch (Exception e) {
            // Captura cualquier imprevisto de infraestructura o base de datos de manera segura
            return Result.failure(ApplicationError.unexpected(
                    "Hopper watch creation",
                    e.getMessage()));
        }
    }
}