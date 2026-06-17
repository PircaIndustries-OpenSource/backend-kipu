package com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.events;

import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.aggregates.SeismicControlSensor;

/**
 * Domain event published when a new {@link SeismicControlSensor} monitor is successfully created and persisted.
 *
 * <p>Other bounded contexts can listen to this event to react to seismic monitoring registration 
 * without directly coupling to the seismic control application services.</p>
 *
 * @param seismicControlId   The identity assigned to the newly created seismic control monitor.
 * @param projectId          The project identifier associated with the monitor.
 * @param sensorId           The unique hardware IoT sensor code.
 * @param unit               The measurement unit for the velocity metrics.
 * @param lastLecture        The initial or last recorded seismic velocity lecture.
 * @param limit              The maximum critical safety threshold allowed.
 * @param location           The physical placement zone inside the construction site.
 * @param timeLecture        The timestamp string of the metric capture.
 * @param state              The operational status integer code of the device.
 */
public record SeismicControlSensorCreatedEvent(
        Long seismicControlId,
        String projectId,
        String sensorId,
        String unit,
        Double lastLecture,
        Double limit,
        String location,
        String timeLecture,
        Integer state) {

    /**
     * Convenience factory that extracts all needed fields from a saved {@link SeismicControlSensor}.
     *
     * @param seismicControl the saved seismic control aggregate (must carry a non-null database id)
     * @return a fully populated {@link SeismicControlSensorCreatedEvent}
     */
    public static SeismicControlSensorCreatedEvent from(SeismicControlSensor seismicControl) {
        return new SeismicControlSensorCreatedEvent(
                seismicControl.getId(),
                seismicControl.getProjectId(),
                seismicControl.getSensorId(),
                seismicControl.getUnit(),
                seismicControl.getLastLecture(),
                seismicControl.getLimit(),
                seismicControl.getLocation(),
                seismicControl.getTimeLecture(),
                seismicControl.getState());
    }
}