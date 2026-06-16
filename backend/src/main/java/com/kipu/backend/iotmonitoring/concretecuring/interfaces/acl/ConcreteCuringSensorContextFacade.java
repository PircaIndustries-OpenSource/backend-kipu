package com.kipu.backend.iotmonitoring.concretecuring.interfaces.acl;

/**
 * ACL facade that exposes Concrete Curing bounded context capabilities to other contexts.
 */
public interface ConcreteCuringSensorContextFacade {

    /**
     * Creates a concrete curing process and returns its identifier.
     *
     * @param projectId          The identifier of the associated construction project.
     * @param sensorId           The physical IoT hardware sensor identifier.
     * @param state              The initial state integer code for the sensor/process.
     * @param location           The specific location description within the job site.
     * @param temperatureReading The initial temperature numeric reading.
     * @param temperatureUnit    The unit of measurement used for temperature (e.g., CELSIUS).
     * @param humidityPercentage The initial humidity percentage level.
     * @param temperatureLimit   The maximum safe temperature threshold configured.
     * @return created concrete curing identifier, or {@code 0L} when creation fails
     */
    Long createConcreteCuring(
            String projectId,
            String sensorId,
            Integer state,
            String location,
            Double temperatureReading,
            String temperatureUnit,
            Double humidityPercentage,
            Double temperatureLimit
    );

    /**
     * Fetches a concrete curing identifier by its physical IoT sensor identifier.
     *
     * @param sensorId The physical IoT sensor identifier.
     * @return concrete curing identifier, or {@code 0L} when not found
     */
    Long fetchConcreteCuringIdBySensorId(String sensorId);
}