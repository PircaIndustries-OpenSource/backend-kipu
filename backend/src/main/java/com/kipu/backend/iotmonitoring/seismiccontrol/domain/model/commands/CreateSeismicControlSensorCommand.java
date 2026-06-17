package com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.commands;

/**
 * Create Seismic Control Command.
 * <p>A record that encapsulates the necessary data from the inbound request 
 * to register and initialize a new seismic monitoring node within a project.</p>
 *
 * @param projectId    The identifier code of the project where the sensor is assigned.
 * @param sensorId     The unique hardware IoT device code.
 * @param unit         The velocity measurement unit (e.g., "mm/s").
 * @param lastLecture  The initial seismic velocity reading value.
 * @param limit        The safety threshold limit allowed for vibrations.
 * @param location     The specific area or placement zone inside the work site.
 * @param timeLecture  The timestamp string of the sensor capture.
 * @param state        The numerical code representing the operational state.
 */
public record CreateSeismicControlSensorCommand(
        String projectId,
        String sensorId,
        String unit,
        Double lastLecture,
        Double limit,
        String location,
        String timeLecture,
        Integer state) {
}