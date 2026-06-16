package com.kipu.backend.iotmonitoring.geolocalization.application.internal.commandservices;

import com.kipu.backend.iotmonitoring.geolocalization.application.commandservices.GeolocalizationSensorCommandService;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.aggregates.GeolocalizationSensor;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.commands.CreateGeolocalizationSensorCommand;
import com.kipu.backend.iotmonitoring.geolocalization.domain.repositories.GeolocalizationSensorRepository;
import com.kipu.backend.shared.application.result.ApplicationError;
import com.kipu.backend.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Geolocalization Sensor Command Service Implementation.
 */
@Service
public class GeolocalizationSensorCommandServiceImpl implements GeolocalizationSensorCommandService {

    private final GeolocalizationSensorRepository geolocalizationSensorRepository;

    /**
     * Constructor.
     *
     * @param geolocalizationSensorRepository The {@link GeolocalizationSensorRepository} instance
     */
    public GeolocalizationSensorCommandServiceImpl(GeolocalizationSensorRepository geolocalizationSensorRepository) {
        this.geolocalizationSensorRepository = geolocalizationSensorRepository;
    }

    // inherited javadoc
    @Override
    public Result<GeolocalizationSensor, ApplicationError> handle(CreateGeolocalizationSensorCommand command) {
        try {
            // 1. Validación de Unicidad de Negocio (Conflict Error)
            // Como mantuvimos sensorId como String primitivo, validamos el comando directamente sin instanciar un VO
            if (command.sensorId() != null && !command.sensorId().isBlank()) {
                if (geolocalizationSensorRepository.existsBySensorId(command.sensorId())) {
                    return Result.failure(ApplicationError.conflict(
                            "GeolocalizationSensor",
                            "A geolocalization sensor with sensor ID '%s' already exists".formatted(command.sensorId())));
                }
            }

            // 2. Creación del Agregado delegando los datos al constructor del Dominio
            var geolocalizationSensor = new GeolocalizationSensor(command);

            // 3. Persistencia en la Base de Datos (MySQL) a través del puerto
            var savedSensor = geolocalizationSensorRepository.save(geolocalizationSensor);

            // 4. Retorno exitoso envuelto en el Result funcional
            return Result.success(savedSensor);

        } catch (IllegalArgumentException e) {
            // Captura errores de validación arrojados por los records o Value Objects (e.g. Coordenadas inválidas)
            return Result.failure(ApplicationError.validationError("GeolocalizationSensor", e.getMessage()));
        } catch (Exception e) {
            // Captura cualquier fallo inesperado del sistema o de la infraestructura
            return Result.failure(ApplicationError.unexpected(
                    "Geolocalization sensor creation",
                    e.getMessage()));
        }
    }
}