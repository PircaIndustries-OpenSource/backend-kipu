package com.kipu.backend.progress.domain.model.valueobjects;

/**
 * Value object representing the lifecycle state of an activity advance.
 * Matches the status expectations from the Angular frontend.
 */
public enum ProgressStatus {
    ACTIVE,
    FINISHED,
    DELAYED
}