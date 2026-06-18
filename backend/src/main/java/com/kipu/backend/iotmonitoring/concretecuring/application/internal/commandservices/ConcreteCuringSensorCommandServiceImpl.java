package com.kipu.backend.iotmonitoring.concretecuring.application.internal.commandservices;

import com.kipu.backend.iotmonitoring.concretecuring.application.commandservices.ConcreteCuringSensorCommandService;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.aggregates.ConcreteCuringSensor;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.commands.CreateConcreteCuringSensorCommand;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.commands.DeleteConcreteCuringSensorCommand;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.commands.UpdateConcreteCuringSensorCommand;
import com.kipu.backend.iotmonitoring.concretecuring.domain.repositories.ConcreteCuringSensorRepository;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.SensorId;
import com.kipu.backend.shared.application.result.ApplicationError;
import com.kipu.backend.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Concrete Curing Sensor Command Service Implementation
 */
@Service
public class ConcreteCuringSensorCommandServiceImpl implements ConcreteCuringSensorCommandService {
    private final ConcreteCuringSensorRepository concreteCuringSensorRepository;

    /**
     * Constructor
     *
     * @param concreteCuringSensorRepository The {@link ConcreteCuringSensorRepository} instance
     */
    public ConcreteCuringSensorCommandServiceImpl(ConcreteCuringSensorRepository concreteCuringSensorRepository) {
        this.concreteCuringSensorRepository = concreteCuringSensorRepository;
    }

    // inherited javadoc
    @Override
    public Result<ConcreteCuringSensor, ApplicationError> handle(CreateConcreteCuringSensorCommand command) {
        try {
            // 1. Regla de Negocio: Validar que el sensor físico no esté asignado a otro proceso activo
            var sensorId = new SensorId(command.sensorId());
            if (concreteCuringSensorRepository.existsBySensorId(sensorId)) {
                return Result.failure(ApplicationError.conflict(
                        "ConcreteCuringSensor",
                        "A concrete curing process with sensor ID '%s' already exists".formatted(command.sensorId())));
            }

            // 2. Instanciación del Agregado de Dominio pasándole el comando completo
            var concreteCuringSensor = new ConcreteCuringSensor(command);

            // 3. Persistencia a través del puerto del dominio (infraestructura se encarga del guardado real)
            var savedConcreteCuringSensor = concreteCuringSensorRepository.save(concreteCuringSensor);

            // 4. Retorno exitoso envuelto en la mónada Result
            return Result.success(savedConcreteCuringSensor);

        } catch (IllegalArgumentException e) {
            // Captura errores de validación de los Value Objects (ej. Humedad fuera de rango 0-100)
            return Result.failure(ApplicationError.validationError("ConcreteCuringSensor", e.getMessage()));
        } catch (Exception e) {
            // Captura fallos inesperados de infraestructura o base de datos
            return Result.failure(ApplicationError.unexpected(
                    "ConcreteCuringSensor creation",
                    e.getMessage()));
        }
    }

    @Override
    public Result<ConcreteCuringSensor, ApplicationError> handle(UpdateConcreteCuringSensorCommand command){
        try {
            var concreteCuringOpt = concreteCuringSensorRepository.findById(command.id());
            if (concreteCuringOpt.isEmpty()){
                return Result.failure(ApplicationError.notFound(
                        "ConcreteCuringSensor",
                        "A concrete curing sensor with ID '%s' was not found".formatted(command.id())
                ));
            }

            var concreteCuring = concreteCuringOpt.get();
            var newSensorId = new SensorId(command.sensorId());

            if (!concreteCuring.getSensorId().equals(newSensorId.value()) && concreteCuringSensorRepository.existsBySensorId(newSensorId)) {
                return Result.failure(ApplicationError.conflict(
                        "ConcreteCuringSensor",
                        "A concrete curing sensor with ID '%s' already exists".formatted(command.id())
                ));
            }

            concreteCuring.update(command);
            var updatedConcreteCuring = concreteCuringSensorRepository.save(concreteCuring);
            return Result.success(updatedConcreteCuring);

        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("HopperWatchSensor", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Hopper watch update", e.getMessage()));
        }
    }

    @Override
    public Result<Void, ApplicationError> handle(DeleteConcreteCuringSensorCommand command) {
        try {
            if (!concreteCuringSensorRepository.existsById(command.id())){
                return Result.failure(ApplicationError.notFound(
                        "ConcreteCuringSensor",
                        "A concrete curing sensor with ID '%s' was not found".formatted(command.id())
                ));
            }

            concreteCuringSensorRepository.deleteById(command.id());
            return Result.success(null);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("ConcreteCuringSensor", e.getMessage()));
        } catch (RuntimeException e) {
            return Result.failure(ApplicationError.unexpected("Concrete curing sensor deletion", e.getMessage()));
        }
    }
}