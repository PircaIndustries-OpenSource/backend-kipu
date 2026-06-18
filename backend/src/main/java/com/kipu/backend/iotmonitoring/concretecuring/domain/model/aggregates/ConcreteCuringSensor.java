package com.kipu.backend.iotmonitoring.concretecuring.domain.model.aggregates;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.commands.CreateConcreteCuringSensorCommand;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.commands.UpdateConcreteCuringSensorCommand;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.events.ConcreteCuringSensorCreatedEvent;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.CuringSensorState;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.Humidity;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.SensorId;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.Temperature;
import com.kipu.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class ConcreteCuringSensor extends AbstractDomainAggregateRoot<ConcreteCuringSensor> {

    @Getter
    @Setter
    private Long id;

    @Getter
    private String projectId;

    private SensorId sensorId;

    @Getter
    @Setter
    private CuringSensorState state;

    @Getter
    private String location;

    private Temperature temperature;
    private Humidity humidity;

    @Getter
    private Double temperatureLimit;

    /**
     * Creates a concrete curing sensor from the provided domain values.
     * @param id Represents the numeric identifier of the concrete curing sensor
     * @param projectId Represents the identifier of the project associated with the Sensor
     * @param sensorId Represents the official identifier of the concrete curing sensor
     * @param state Represents the state of the concrete curing sensor
     * @param location Represents the location of the concrete curing sensor
     * @param temperature Represents the temperature detected by the concrete curing sensor
     * @param humidity Represents the humidity percentage detected by the concrete curing sensor
     * @param temperatureLimit Represents the set temperature limit on the concrete curing sensor
     */
    public ConcreteCuringSensor(Long id, String projectId, SensorId sensorId, CuringSensorState state, String location, Temperature temperature, Humidity humidity, Double temperatureLimit){
        this.id = id;
        this.projectId = Objects.requireNonNull(projectId, "projectId must not be null.");
        this.sensorId = Objects.requireNonNull(sensorId, "sensorId must not be null.");
        this.state = Objects.requireNonNull(state, "state must not be null.");
        this.location = Objects.requireNonNull(location, "location must not be null.");
        this.temperature = Objects.requireNonNull(temperature, "temperature must not be null.");
        this.humidity = Objects.requireNonNull(humidity, "humidity must not be null.");
        this.temperatureLimit = Objects.requireNonNull(temperatureLimit, "temperatureLimit must not be null.");

        // Regla de negocio intrínseca: Validar alertas al instanciar
        checkTemperatureAlert();
    }

    /**
     * Creates a concrete curing sensor without ID from the provided domain values (user before persistence).
     * @param projectId Represents the identifier of the project associated with the Sensor
     * @param sensorId Represents the official identifier of the concrete curing sensor
     * @param state Represents the state of the concrete curing sensor
     * @param location Represents the location of the concrete curing sensor
     * @param temperature Represents the temperature detected by the concrete curing sensor
     * @param humidity Represents the humidity percentage detected by the concrete curing sensor
     * @param temperatureLimit Represents the set temperature limit on the concrete curing sensor
     */
    public ConcreteCuringSensor(String projectId, SensorId sensorId, CuringSensorState state, String location, Temperature temperature, Humidity humidity, Double temperatureLimit) {
        this(null, projectId, sensorId, state, location, temperature, humidity, temperatureLimit);
    }

    /**
     * Flattened constructor wrapping primitive and standard types into Value Objects.
     * @param projectId Represents the identifier of the project associated with the Sensor
     * @param sensorId Represents the official identifier of the concrete curing sensor
     * @param stateOrdinal Represents the number of the state of the concrete curing sensor
     * @param location Represents the location of the concrete curing sensor
     * @param temperatureReading Represents the temperature read by the concrete curing sensor
     * @param unit Represents the unit the temperature is being read from
     * @param humidityPercentage Represents the humidity percentage detected by the concrete curing sensor
     * @param temperatureLimit Represents the set temperature limit on the concrete curing sensor
     */
    public ConcreteCuringSensor(String projectId, String sensorId, Integer stateOrdinal, String location, Double temperatureReading, String unit, Integer humidityPercentage, Double temperatureLimit){
        this (
                projectId,
                new SensorId(sensorId),
                CuringSensorState.fromValue(stateOrdinal),
                location,
                new Temperature(temperatureReading, unit),
                new Humidity(humidityPercentage),
                temperatureLimit
        );
    }

    /**
     * Constructor driving instantiation via a CreateConcreteCuringSensorCommand.
     * @param command The {@link CreateConcreteCuringSensorCommand} instance
     */
    public ConcreteCuringSensor(CreateConcreteCuringSensorCommand command) {
        this(
                command.projectId(),
                command.sensorId(),
                command.state(),
                command.location(),
                command.temperature(),
                command.unit(),
                command.humidity(),
                command.limit()
        );
    }

    // --- Value Object Unwrapping Getters ---

    /**
     * Raw sensor hardware identification getter for external communication.
     * @return Hardware code string
     */
    public String getSensorId() {
        return sensorId.value();
    }

    public CuringSensorState getState() {
        return state;
    }

    public Integer getStateValue() {
        return state.getValue();
    }

    public Temperature getTemperatureValue() {
        return temperature;
    }

    public Humidity getHumidityValue() {
        return humidity;
    }

    public Double getTemperatureReading() {
        return temperature.reading();
    }

    public String getTemperatureUnit() {
        return temperature.unit();
    }

    public String getFullTemperatureMeasure() {
        return temperature.getFullMeasure();
    }

    public Integer getHumidityPercentage() {
        return humidity.percentage();
    }

    // --- Domain Business Logic ---

    /**
     * Updates the current telemetry data and recalculates state constraints.
     */
    public void updateTelemetry(Temperature newTemperature, Humidity newHumidity) {
        this.temperature = Objects.requireNonNull(newTemperature, "newTemperature must not be null");
        this.humidity = Objects.requireNonNull(newHumidity, "newHumidity must not be null");
        checkTemperatureAlert();
    }

    public void update(UpdateConcreteCuringSensorCommand command){
        this.sensorId = new SensorId(command.sensorId());
        this.state = CuringSensorState.fromValue(command.state());
        this.location = command.location();
        this.humidity = new Humidity(command.humidity());
        this.temperature = new Temperature(command.temperature(), command.unit());
        this.temperatureLimit = command.limit();
    }

    private void checkTemperatureAlert() {
        if (this.temperature.reading() > this.temperatureLimit) {
            this.state = CuringSensorState.ALERT;
        }
    }

    /**
     * Signals that this aggregate has just been created and persisted.
     * Registers a {@link ConcreteCuringSensorCreatedEvent} for infrastructure publishing.
     */
    public void onCreated() {
        registerDomainEvent(ConcreteCuringSensorCreatedEvent.from(this));
    }
}
