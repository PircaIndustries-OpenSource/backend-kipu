package com.kipu.backend.iotmonitoring.hopperwatch.domain.model.aggregates;

import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.commands.CreateHopperWatchSensorCommand;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.events.HopperWatchSensorCreatedEvent;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.valueobjects.HopperMeasurement;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.commands.
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.valueobjects.HopperSensorState;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.valueobjects.SensorId;
import com.kipu.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * HopperWatchSensor aggregate root.
 *
 * <p>Extends {@link AbstractDomainAggregateRoot} to gain domain event registration
 * support. No JPA or persistence annotation is present here -- those concerns live
 * exclusively in {@code HopperWatchSensorPersistenceEntity}.</p>
 */
public class HopperWatchSensor extends AbstractDomainAggregateRoot<HopperWatchSensor> {

    @Getter
    @Setter
    private Long id;

    @Getter
    private String projectId;

    private SensorId sensorId;

    @Getter
    private String name;

    @Getter
    private String unit;

    private HopperSensorState state;
    private HopperMeasurement measurement;

    /**
     * Creates a hopper watch monitor from the provided domain values.
     */
    public HopperWatchSensor(Long id, String projectId, SensorId sensorId, String name, String unit, HopperSensorState state, HopperMeasurement measurement) {
        this.id = id;
        this.projectId = Objects.requireNonNull(projectId, "projectId must not be null");
        this.sensorId = Objects.requireNonNull(sensorId, "sensorId must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.unit = Objects.requireNonNull(unit, "unit must not be null");
        this.state = Objects.requireNonNull(state, "state must not be null");
        this.measurement = Objects.requireNonNull(measurement, "measurement must not be null");
    }

    /**
     * Creates a hopper watch monitor from the provided domain values (without ID).
     */
    public HopperWatchSensor(String projectId, SensorId sensorId, String name, String unit, HopperSensorState state, HopperMeasurement measurement) {
        // CORREGIDO: Se cambió 'this.this' por 'this' heredando correctamente al constructor principal
        this(null, projectId, sensorId, name, unit, state, measurement);
    }

    /**
     * Constructor with flat primitive parameters.
     */
    public HopperWatchSensor(String projectId, String sensorId, String name, String unit, Integer state, Integer lastLecture, Integer limit) {
        this(
                projectId,
                new SensorId(sensorId),
                name,
                unit,
                HopperSensorState.fromValue(state),
                new HopperMeasurement(lastLecture, limit)
        );
    }

    /**
     * Constructor with a CreateHopperWatchSensorCommand.
     * @param command The {@link CreateHopperWatchSensorCommand} instance
     */
    public HopperWatchSensor(CreateHopperWatchSensorCommand command) {
        this(
                command.projectId(),
                command.sensorId(),
                command.name(),
                command.unit(),
                command.state(),
                command.lastLecture(),
                command.limit()
        );
    }

    /**
     * Raw sensor hardware identification getter for external communication.
     * @return Hardware code string
     */
    public String getSensorId() {
        return sensorId.value();
    }

    /**
     * Rich state enum getter.
     * @return The {@link HopperSensorState} enum instance.
     */
    public HopperSensorState getState() {
        return state;
    }

    /**
     * Flat numerical state getter for infrastructure/external communication.
     * @return Current state integer representation
     */
    public Integer getStateValue() {
        return state.getValue();
    }

    /**
     * Rich measurement Value Object getter.
     * @return The {@link HopperMeasurement} instance.
     */
    public HopperMeasurement getMeasurementValue() {
        return measurement;
    }

    /**
     * Flat last lecture getter.
     * @return Value of the last recorded lecture
     */
    public Integer getLastLecture() {
        return measurement.lastLecture();
    }

    /**
     * Flat threshold limit getter.
     * @return Value of the safety threshold limit
     */
    public Integer getLimit() {
        return measurement.safetyLimit();
    }

    /**
     * Updates the custom descriptive name of the hopper.
     * @param name The new assigned name
     */
    public void updateName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    /**
     * Command-driven updater for smooth Application Layer integration.
     * @param command The {@link { UpdateHopperWatchSensorCommand } record.
     */
    public void update(UpdateHopperWatchSensorCommand command) {
        this.sensorId = new SensorId(command.sensorId());
        this.name = command.name();
        this.unit = command.unit();
        this.state = HopperSensorState.fromValue(command.state());
        this.measurement = new HopperMeasurement(command.lastLecture(), command.limit());
    }

    /**
     * Signals that this hopper watch monitor has just been created and persisted.
     *
     * <p>Called by the repository adapter after the JPA identity has been assigned.
     * Registers a {@link HopperWatchSensorCreatedEvent} so the infrastructure can publish it
     * to interested subscribers in other bounded contexts.</p>
     */
    public void onCreated() {
        registerDomainEvent(HopperWatchSensorCreatedEvent.from(this));
    }
}