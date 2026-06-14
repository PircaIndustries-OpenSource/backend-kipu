package com.kipu.backend.Logistics.domain.model.aggregates;

import com.kipu.backend.Logistics.domain.model.valueobjects.*;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.BudgetLineId;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.UserId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MaterialRequest {

    private final Long id;
    private final Instant deadline;
    private final RequestStatus requestStatus;
    private final RequestPriority requestPriority;
    private final String deliveryLocation;
    private final BudgetLineId budgetLineId;
    private final String purpose;
    private final String additionalNotes;
    private final UserId requestedBy;
    private final List<MaterialRequestItem> items;
    private final Instant createdAt;
    private final Instant updatedAt;

    private MaterialRequest(Long id, Instant deadline, RequestStatus requestStatus,
                            RequestPriority requestPriority, String deliveryLocation,
                            BudgetLineId budgetLineId, String purpose, String additionalNotes,
                            UserId requestedBy, List<MaterialRequestItem> items,
                            Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.deadline = Objects.requireNonNull(deadline, "material.request.error.deadline.notBlank");
        this.requestStatus = Objects.requireNonNull(requestStatus, "material.request.error.requestStatus.notBlank");
        this.requestPriority = Objects.requireNonNull(requestPriority, "material.request.error.requestPriority.notBlank");
        this.deliveryLocation = Objects.requireNonNull(deliveryLocation, "material.request.error.deliveryLocation.notBlank");
        this.budgetLineId = Objects.requireNonNull(budgetLineId, "material.request.error.budgetLineId.notBlank");
        this.purpose = purpose;
        this.additionalNotes = additionalNotes;
        this.requestedBy = Objects.requireNonNull(requestedBy, "material.request.error.requestedBy.notBlank");
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MaterialRequest create(Instant deadline, RequestPriority requestPriority,
                                         String deliveryLocation, BudgetLineId budgetLineId,
                                         String purpose, String additionalNotes, UserId requestedBy,
                                         List<MaterialRequestItem> items) {
        Instant now = Instant.now();
        return new MaterialRequest(null, deadline, RequestStatus.PENDING, requestPriority,
                deliveryLocation, budgetLineId, purpose, additionalNotes, requestedBy, items, now, now);
    }

    public static MaterialRequest rehydrate(Long id, Instant deadline, RequestStatus requestStatus,
                                            RequestPriority requestPriority, String deliveryLocation,
                                            BudgetLineId budgetLineId, String purpose, String additionalNotes,
                                            UserId requestedBy, List<MaterialRequestItem> items,
                                            Instant createdAt, Instant updatedAt) {
        return new MaterialRequest(id, deadline, requestStatus, requestPriority, deliveryLocation,
                budgetLineId, purpose, additionalNotes, requestedBy, items, createdAt, updatedAt);
    }

    public MaterialRequest update(Instant deadline, RequestPriority requestPriority,
                                   String deliveryLocation, String purpose, String additionalNotes,
                                   List<MaterialRequestItem> items) {
        return new MaterialRequest(
                this.id,
                deadline != null ? deadline : this.deadline,
                this.requestStatus,
                requestPriority != null ? requestPriority : this.requestPriority,
                deliveryLocation != null ? deliveryLocation : this.deliveryLocation,
                this.budgetLineId,
                purpose != null ? purpose : this.purpose,
                additionalNotes != null ? additionalNotes : this.additionalNotes,
                this.requestedBy,
                items != null ? items : this.items,
                this.createdAt, Instant.now()
        );
    }

    public MaterialRequest withStatus(RequestStatus newStatus) {
        return new MaterialRequest(
                this.id, this.deadline, newStatus, this.requestPriority,
                this.deliveryLocation, this.budgetLineId, this.purpose,
                this.additionalNotes, this.requestedBy, this.items,
                this.createdAt, Instant.now()
        );
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public RequestPriority getRequestPriority() {
        return requestPriority;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public BudgetLineId getBudgetLineId() {
        return budgetLineId;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public UserId getRequestedBy() {
        return requestedBy;
    }

    public List<MaterialRequestItem> getItems() {
        return new ArrayList<>(items);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}