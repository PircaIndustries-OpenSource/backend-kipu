package com.kipu.backend.iotmonitoring.geolocalization.domain.model.aggregates;

import com.kipu.backend.iotmonitoring.geolocalization.domain.model.commands.CreateGeolocalizationSensorCommand;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.events.GeolocalizationAssetCreatedEvent;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.valueobjects.Coordinates;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.valueobjects.GeolocalizationSensorState;
import com.kipu.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * GeolocalizationSensor aggregate root.
 */
public class GeolocalizationSensor extends AbstractDomainAggregateRoot<GeolocalizationSensor> {

    @Getter
    @Setter
    private Long id;

    @Getter
    private String projectId;

    @Getter
    private String sensorId;

    @Getter
    private Integer numberId;

    @Getter
    private String name;

    @Getter
    private GeolocalizationSensorState state;

    private Coordinates coordinates;

    /**
     * Base Domain Constructor.
     */
    public GeolocalizationSensor(Long id, String projectId, String sensorId, Integer numberId, String name, GeolocalizationState state, Coordinates coordinates) {
        this.id = id;
        this.projectId = Objects.requireNonNull(projectId, "projectId must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.state = Objects.requireNonNull(state, "state must not be null");
        this.coordinates = Objects.requireNonNull(coordinates, "coordinates must not be null");
        this.sensorId = sensorId;
        this.numberId = numberId;
    }

    public GeolocalizationSensor(String projectId, String sensorId, Integer numberId, String name, GeolocalizationState state, Coordinates coordinates) {
        this(null, projectId, sensorId, numberId, name, state, coordinates);
    }

    /**
     * Constructor with flattened primitive variables (Transforms Integer to Enum).
     */
    public GeolocalizationSensor(String projectId, String sensorId, Integer numberId, String name, Integer state, Double longitude, Double latitude) {
        this(
                projectId,
                sensorId,
                numberId,
                name,
                GeolocalizationSensor.fromInteger(state), // Aquí ocurre la magia del mapeo
                new Coordinates(longitude, latitude));
    }

    /**
     * Constructor with a CreateGeolocalizationCommand.
     */
    public GeolocalizationSensor(CreateGeolocalizationSensorCommand command) {
        this(
                command.projectId(),
                command.sensorId(),
                command.numberId(),
                command.name(),
                command.state(), // Pasa el Integer directo, el constructor de arriba lo traduce
                command.longitude(),
                command.latitude());
    }

    public Coordinates getCoordinatesValue() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = Objects.requireNonNull(coordinates, "coordinates must not be null");
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    public void setState(GeolocalizationSensorState state) {
        this.state = Objects.requireNonNull(state, "state must not be null");
    }

    public String getFormattedCoordinates() {
        return coordinates.getFormattedCoordinates();
    }

    public Double getLongitude() {
        return coordinates.longitude();
    }

    public Double getLatitude() {
        return coordinates.latitude();
    }

    public void onCreated() {
        registerDomainEvent(GeolocalizationSensorCreatedEvent.from(this));
    }
}