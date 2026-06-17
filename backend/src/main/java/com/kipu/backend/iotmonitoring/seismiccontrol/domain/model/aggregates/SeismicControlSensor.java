package com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.aggregates;

import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.commands.CreateSeismicControlSensorCommand;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.events.SeismicControlSensorCreatedEvent;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.valueobjects.SeismicControlSensorState;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.valueobjects.SeismicTelemetry;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.valueobjects.SensorId;
import com.kipu.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * SeismicControlSensor aggregate root.
 *
 * <p>Extends {@link AbstractDomainAggregateRoot} to gain domain event registration
 * support. No JPA or persistence annotation is present here -- those concerns live
 * exclusively in the infrastructure layer.</p>
 */
public class SeismicControlSensor extends AbstractDomainAggregateRoot<SeismicControlSensor> {

    @Getter
    @Setter
    private Long id;

    @Getter
    private String projectId;

    private SensorId sensorId;
    private String unit;
    private SeismicTelemetry telemetry;

    @Getter
    private String location;

    @Getter
    private String timeLecture;

    private SeismicControlSensorState state;

    /**
     * Primary constructor with rich domain Value Objects.
     */
    public SeismicControlSensor(Long id, String projectId, SensorId sensorId, String unit, SeismicTelemetry telemetry, String location, String timeLecture, SeismicControlSensorState state) {
        this.id = id;
        this.projectId = Objects.requireNonNull(projectId, "projectId must not be null");
        this.sensorId = Objects.requireNonNull(sensorId, "sensorId must not be null");
        this.unit = Objects.requireNonNull(unit, "unit must not be null");
        this.telemetry = Objects.requireNonNull(telemetry, "telemetry must not be null");
        this.location = Objects.requireNonNull(location, "location must not be null");
        this.timeLecture = Objects.requireNonNull(timeLecture, "timeLecture must not be null");
        this.state = Objects.requireNonNull(state, "state must not be null");
    }

    /**
     * Domain constructor without database-assigned ID.
     */
    public SeismicControlSensor(String projectId, SensorId sensorId, String unit, SeismicTelemetry telemetry, String location, String timeLecture, SeismicControlSensorState state) {
        this(null, projectId, sensorId, unit, telemetry, location, timeLecture, state);
    }

    /**
     * Flattened constructor converting primitives and raw strings into domain concepts.
     */
    public SeismicControlSensor(String projectId, String sensorId, String unit, Double lastLecture, Double limit, String location, String timeLecture, Integer state) {
        this(
                projectId,
                new SensorId(sensorId),
                unit,
                new SeismicTelemetry(lastLecture, limit),
                location,
                timeLecture,
                SeismicControlSensorState.fromInteger(state)
        );
    }

    /**
     * Command-driven constructor for smooth Application Layer integration.
     * @param command The {@link CreateSeismicControlSensorCommand} record.
     */
    public SeismicControlSensor(CreateSeismicControlSensorCommand command) {
        this(
                command.projectId(),
                command.sensorId(),
                command.unit(),
                command.lastLecture(),
                command.limit(),
                command.location(),
                command.timeLecture(),
                command.state()
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
     * Measurement metric unit getter.
     */
    public String getUnit() {
        return this.unit;
    }

    /**
     * Last telemetry lecture numeric value getter.
     */
    public Double getLastLecture() {
        return telemetry.lastLecture();
    }

    /**
     * Safety threshold numeric limit getter.
     */
    public Double getLimit() {
        return telemetry.limit();
    }

    /**
     * Standardized integer value getter for persistence entity and outbound DTO mapping.
     * @return State integer code (1, 2, 3)
     */
    public Integer getState() {
        return this.state.getValue();
    }

    /**
     * Internal domain-specific validation state getter.
     * @return The actual {@link SeismicControlSensorState} enum instance.
     */
    public SeismicControlSensorState getSeismicControlSensorStateValue() {
        return this.state;
    }

    /**
     * Verifies if the node is currently recording a metric that surpasses safety margins.
     * @return true if telemetry limits are breached.
     */
    public boolean isDeviceAlertActive() {
        return telemetry.isExceedingLimit();
    }

    /**
     * Dispatches the domain creation fact asynchronously to other listening bounded contexts.
     */
    public void onCreated() {
        registerDomainEvent(SeismicControlSensorCreatedEvent.from(this));
    }
    /**
     * Rich telemetry Value Object getter.
     * @return The {@link SeismicTelemetry} instance.
     */
    public SeismicTelemetry getTelemetryValue() {
        return this.telemetry;
    }
}