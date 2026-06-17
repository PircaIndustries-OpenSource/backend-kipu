package com.kipu.backend.iotmonitoring.hopperwatch.interfaces.acl;

/**
 * ACL facade that exposes Hopper Watch bounded context capabilities to other contexts.
 */
public interface HopperWatchSensorContextFacade {

    /**
     * Creates a hopper watch monitor and returns its identifier.
     *
     * @param projectId   The project identity associated with this hopper watch
     * @param sensorId    The unique hardware sensor identifier
     * @param name        The name of the hopper watch monitor
     * @param unit        The measurement unit (e.g., kg, m3)
     * @param state       The initial state value (integer representation).
     * @param lastLecture The initial telemetry measurement value
     * @param safetyLimit The defined safety threshold limit for alerts
     * @return created hopper watch identifier, or {@code 0L} when creation fails
     */
    Long createHopperWatchSensor(String projectId, String sensorId, String name, Integer state, String unit, Integer lastLecture, Integer safetyLimit);

    /**
     * Fetches a hopper watch identifier by its unique hardware sensor ID.
     *
     * @param sensorId hopper watch hardware sensor identifier
     * @return hopper watch identifier, or {@code 0L} when not found
     */
    Long fetchHopperWatchSensorIdBySensorId(String sensorId);
}