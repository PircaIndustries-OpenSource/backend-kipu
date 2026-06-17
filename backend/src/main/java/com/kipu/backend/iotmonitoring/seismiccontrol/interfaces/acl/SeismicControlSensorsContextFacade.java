package com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.acl;

/**
 * ACL facade that exposes Seismic Control bounded context capabilities to other contexts.
 * <p>Acts as an outbound entry point or shared contract within the platform,
 * shielding external consumers from internal domain complexities (Commands/Queries).</p>
 */
public interface SeismicControlSensorsContextFacade {

    /**
     * Creates a seismic control sensor node and returns its internal database identifier.
     *
     * @param projectId   The associated architectural project identifier.
     * @param sensorId    The physical hardware identification code.
     * @param unit        The measurement metric unit.
     * @param lastLecture The last recorded seismic telemetry value.
     * @param limit       The safety threshold numeric limit.
     * @param location    The physical installation location description.
     * @param timeLecture The timestamp text of the measurement lecture.
     * @param state       The status integer code of the sensor.
     * @return created seismic control sensor identifier, or {@code 0L} when creation fails.
     */
    Long createSeismicControlSensor(
            String projectId,
            String sensorId,
            String unit,
            Double lastLecture,
            Double limit,
            String location,
            String timeLecture,
            Integer state
    );

    /**
     * Fetches a seismic control sensor internal identifier by its physical hardware code.
     *
     * @param sensorId physical hardware identification code
     * @return seismic control sensor internal identifier, or {@code 0L} when not found
     */
    Long fetchSeismicControlSensorIdBySensorId(String sensorId);
}