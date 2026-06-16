package com.kipu.backend.iotmonitoring.geolocalization.interfaces.acl;

/**
 * ACL facade that exposes Geolocalization bounded context capabilities to other contexts.
 */
public interface GeolocalizationSensorContextFacade {

    /**
     * Creates a geolocalization sensor and returns its identifier.
     *
     * @param projectId   The project identifier associated with the sensor.
     * @param sensorId    The physical IoT sensor identifier.
     * @param numberId    The internal sequential number assigned to the sensor.
     * @param name        The name assigned to the sensor.
     * @param state       The initial state value (integer representation).
     * @param longitude   The initial longitude coordinate component.
     * @param latitude    The initial latitude coordinate component.
     * @return created geolocalization sensor identifier, or {@code 0L} when creation fails
     */
    Long createGeolocalizationSensor(
            String projectId,
            String sensorId,
            Integer numberId,
            String name,
            Integer state,
            double longitude,
            double latitude
    );

    /**
     * Fetches a geolocalization sensor identifier by its physical IoT sensor identifier.
     *
     * @param sensorId The physical IoT sensor identifier.
     * @return geolocalization sensor identifier, or {@code 0L} when not found
     */
    Long fetchGeolocalizationSensorIdBySensorId(String sensorId);
}