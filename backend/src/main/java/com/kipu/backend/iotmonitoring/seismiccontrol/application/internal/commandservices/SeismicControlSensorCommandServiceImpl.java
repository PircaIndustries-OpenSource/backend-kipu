package com.kipu.backend.iotmonitoring.seismiccontrol.application.internal.commandservices;

import com.kipu.backend.iotmonitoring.seismiccontrol.application.commandservices.SeismicControlSensorCommandService;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.aggregates.SeismicControlSensor;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.commands.CreateSeismicControlSensorCommand;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.valueobjects.SensorId;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.repositories.SeismicControlSensorRepository;
import com.kipu.backend.shared.application.result.ApplicationError;
import com.kipu.backend.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Seismic Control Sensor Command Service Implementation.
 * <p>Orchestrates the business logic required to validate and safely instantiate
 * new tracking hardware nodes within the architectural cloud environment.</p>
 */
@Service
public class SeismicControlSensorCommandServiceImpl implements SeismicControlSensorCommandService {

    private final SeismicControlSensorRepository seismicControlSensorRepository;

    /**
     * Component constructor for dependency injection.
     *
     * @param seismicControlSensorRepository The {@link SeismicControlSensorRepository} domain port.
     */
    public SeismicControlSensorCommandServiceImpl(SeismicControlSensorRepository seismicControlSensorRepository) {
        this.seismicControlSensorRepository = seismicControlSensorRepository;
    }

    @Override
    public Result<SeismicControlSensor, ApplicationError> handle(CreateSeismicControlSensorCommand command) {
        try {
            // 1. Validar unicidad del identificador físico del hardware (SensorId)
            var sensorId = new SensorId(command.sensorId());
            if (seismicControlSensorRepository.existsBySensorId(sensorId)) {
                return Result.failure(ApplicationError.conflict(
                        "SeismicControlSensor",
                        "A seismic control sensor with hardware ID '%s' already exists".formatted(command.sensorId())));
            }

            // 2. Instanciar el agregado puro delegando el mapeo a su constructor de comando
            var seismicControlSensor = new SeismicControlSensor(command);

            // 3. Persistir a través del adaptador de infraestructura y retornar el resultado exitoso
            var savedSensor = seismicControlSensorRepository.save(seismicControlSensor);
            return Result.success(savedSensor);

        } catch (IllegalArgumentException e) {
            // Captura errores de validación de negocio interceptados por Objects.requireNonNull o invariantes del VO
            return Result.failure(ApplicationError.validationError("SeismicControlSensor", e.getMessage()));
        } catch (Exception e) {
            // Captura fallos críticos inesperados o caídas imprevistas de conectividad con la infraestructura
            return Result.failure(ApplicationError.unexpected(
                    "Seismic control sensor creation",
                    e.getMessage()));
        }
    }
}