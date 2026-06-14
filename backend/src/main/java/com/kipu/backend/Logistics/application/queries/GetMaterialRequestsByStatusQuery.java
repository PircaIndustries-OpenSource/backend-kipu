package com.kipu.backend.Logistics.application.queries;
import com.kipu.backend.Logistics.domain.model.valueobjects.RequestStatus;

public record GetMaterialRequestsByStatusQuery(RequestStatus status) {
    public GetMaterialRequestsByStatusQuery {
        if (status == null)
            throw new IllegalArgumentException("status cannot be null");
    }
}