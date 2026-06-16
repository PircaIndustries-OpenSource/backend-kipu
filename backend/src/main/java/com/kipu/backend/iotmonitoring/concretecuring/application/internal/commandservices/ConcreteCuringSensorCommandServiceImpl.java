package com.kipu.backend.iotmonitoring.concretecuring.application.internal.commandservices;

import com.kipu.backend.iotmonitoring.concretecuring.application.commandservices.ConcreteCuringSensorCommandService;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.aggregates.ConcreteCuringSensor;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.commands.CreateConcreteCuringSensorCommand;
import com.kipu.backend.iotmonitoring.concretecuring.domain.repositories.ConcreteCuringSensorRepository;
import com.kipu.backend.shared.application.result.ApplicationError;
import com.kipu.backend.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Concrete Curing Command Service Implementation
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
            if (concreteCuringSensorRepository.existsBySensorId(command.sensorId())) {
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
}