package com.kipu.backend.iotmonitoring.concretecuring.application.acl;

import com.kipu.backend.iotmonitoring.concretecuring.application.commandservices.ConcreteCuringSensorCommandService;
import com.kipu.backend.iotmonitoring.concretecuring.application.queryservices.ConcreteCuringSensorQueryService;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.aggregates.ConcreteCuringSensor;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.commands.CreateConcreteCuringSensorCommand;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.queries.GetConcreteCuringSensorBySensorIdQuery;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.CuringSensorState;
import com.kipu.backend.iotmonitoring.concretecuring.interfaces.acl.ConcreteCuringSensorContextFacade;
import org.springframework.stereotype.Service;

/**
 * Application-layer implementation of the Concrete Curing ACL facade.
 *
 * <p>Provides a simplified integration surface for other bounded contexts that need concrete curing
 * operations without coupling to Concrete Curing internal models.</p>
 */
@Service
public class ConcreteCuringSensorContextFacadeImpl implements ConcreteCuringSensorContextFacade {
    private final ConcreteCuringSensorCommandService concreteCuringSensorCommandService;
    private final ConcreteCuringSensorQueryService concreteCuringSensorQueryService;

    public ConcreteCuringSensorContextFacadeImpl(
            ConcreteCuringSensorCommandService concreteCuringSensorCommandService,
            ConcreteCuringSensorQueryService concreteCuringSensorQueryService) {
        this.concreteCuringSensorCommandService = concreteCuringSensorCommandService;
        this.concreteCuringSensorQueryService = concreteCuringSensorQueryService;
    }

    /**
     * Creates a concrete curing process through the Concrete Curing application command service.
     *
     * @return created concrete curing identifier, or {@code 0L} when creation fails
     */
    @Override
    public Long createConcreteCuring(
            String projectId,
            String sensorId,
            Integer state,
            String location,
            Double temperatureReading,
            String temperatureUnit,
            Integer humidityPercentage,
            Double temperatureLimit) {

        try {
            // 1. Traducimos el entero primitivo que viene de fuera al Enum interno de nuestro dominio
            var sensorState = CuringSensorState.values()[state];

            // 2. Mapeamos y empaquetamos los primitivos en los Value Objects internos para construir el Comando
            var createConcreteCuringSensorCommand = new CreateConcreteCuringSensorCommand(
                    projectId,
                    sensorId,
                    state,
                    location,
                    temperatureReading,
                    temperatureUnit,
                    humidityPercentage,
                    temperatureLimit
            );

            // 3. Enviamos el comando al servicio de aplicación dedicado
            var result = concreteCuringSensorCommandService.handle(createConcreteCuringSensorCommand);

            // 4. Si fue exitoso extraemos el ID técnico, si falló por validación o negocio devolvemos 0L de forma segura
            return result.toOptional()
                    .map(ConcreteCuringSensor::getId)
                    .orElse(0L);

        } catch (Exception e) {
            // Protección ACL: Si ocurre un error de conversión (ej. índice de enum fuera de rango), no rompe el hilo externo
            return 0L;
        }
    }

    /**
     * Fetches a concrete curing identifier by its physical sensor ID through the query service.
     *
     * @param sensorId The physical IoT sensor identifier.
     * @return concrete curing identifier, or {@code 0L} when no process is found
     */
    @Override
    public Long fetchConcreteCuringIdBySensorId(String sensorId) {
        var getConcreteCuringSensorBySensorIdQuery = new GetConcreteCuringSensorBySensorIdQuery(sensorId);
        var concreteCuring = concreteCuringSensorQueryService.handle(getConcreteCuringSensorBySensorIdQuery);

        return concreteCuring.map(ConcreteCuringSensor::getId).orElse(0L);
    }
}