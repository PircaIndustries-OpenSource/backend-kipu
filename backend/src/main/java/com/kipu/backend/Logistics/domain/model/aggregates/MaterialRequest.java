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
    private final String projectId;
    private final Instant deadline;
    private final RequestStatus requestStatus;
    private final RequestPriority requestPriority;
    private final String deliveryLocation;
    private final BudgetLineId budgetLineId;
    private final String purpose;
    private final String additionalNotes;
    private final UserId requestedBy;
    private final SupplierId suggestedSupplierId;
    private final List<MaterialRequestItem> items;
    private final Instant createdAt;
    private final Instant updatedAt;

    private MaterialRequest(Long id, String projectId, Instant deadline, RequestStatus requestStatus,
                            RequestPriority requestPriority, String deliveryLocation,
                            BudgetLineId budgetLineId, String purpose, String additionalNotes,
                            UserId requestedBy, SupplierId suggestedSupplierId,
                            List<MaterialRequestItem> items,
                            Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.projectId = projectId;
        this.deadline = Objects.requireNonNull(deadline, "material.request.error.deadline.notBlank");
        this.requestStatus = Objects.requireNonNull(requestStatus, "material.request.error.requestStatus.notBlank");
        this.requestPriority = Objects.requireNonNull(requestPriority, "material.request.error.requestPriority.notBlank");
        this.deliveryLocation = Objects.requireNonNull(deliveryLocation, "material.request.error.deliveryLocation.notBlank");
        this.budgetLineId = budgetLineId;
        this.purpose = purpose;
        this.additionalNotes = additionalNotes;
        this.requestedBy = Objects.requireNonNull(requestedBy, "material.request.error.requestedBy.notBlank");
        this.suggestedSupplierId = suggestedSupplierId;
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MaterialRequest create(String projectId, Instant deadline, RequestPriority requestPriority,
                                         String deliveryLocation, BudgetLineId budgetLineId,
                                         String purpose, String additionalNotes, UserId requestedBy,
                                         SupplierId suggestedSupplierId, List<MaterialRequestItem> items) {
        Instant now = Instant.now();
        return new MaterialRequest(null, projectId, deadline, RequestStatus.PENDING, requestPriority,
                deliveryLocation, budgetLineId, purpose, additionalNotes, requestedBy,
                suggestedSupplierId, items, now, now);
    }

    public static MaterialRequest rehydrate(Long id, String projectId, Instant deadline, RequestStatus requestStatus,
                                            RequestPriority requestPriority, String deliveryLocation,
                                            BudgetLineId budgetLineId, String purpose, String additionalNotes,
                                            UserId requestedBy, SupplierId suggestedSupplierId,
                                            List<MaterialRequestItem> items,
                                            Instant createdAt, Instant updatedAt) {
        return new MaterialRequest(id, projectId, deadline, requestStatus, requestPriority, deliveryLocation,
                budgetLineId, purpose, additionalNotes, requestedBy, suggestedSupplierId, items, createdAt, updatedAt);
    }

    public MaterialRequest update(Instant deadline, RequestPriority requestPriority,
                                   String deliveryLocation, String purpose, String additionalNotes,
                                   List<MaterialRequestItem> items) {
        return new MaterialRequest(
                this.id, this.projectId,
                deadline != null ? deadline : this.deadline,
                this.requestStatus,
                requestPriority != null ? requestPriority : this.requestPriority,
                deliveryLocation != null ? deliveryLocation : this.deliveryLocation,
                this.budgetLineId,
                purpose != null ? purpose : this.purpose,
                additionalNotes != null ? additionalNotes : this.additionalNotes,
                this.requestedBy,
                this.suggestedSupplierId,
                items != null ? items : this.items,
                this.createdAt, Instant.now()
        );
    }

    public MaterialRequest withStatus(RequestStatus newStatus) {
        return new MaterialRequest(
                this.id, this.projectId, this.deadline, newStatus, this.requestPriority,
                this.deliveryLocation, this.budgetLineId, this.purpose,
                this.additionalNotes, this.requestedBy, this.suggestedSupplierId,
                this.items, this.createdAt, Instant.now()
        );
    }

    public Long getId() { return id; }
    public String getProjectId() { return projectId; }
    public Instant getDeadline() { return deadline; }
    public RequestStatus getRequestStatus() { return requestStatus; }
    public RequestPriority getRequestPriority() { return requestPriority; }
    public String getDeliveryLocation() { return deliveryLocation; }
    public BudgetLineId getBudgetLineId() { return budgetLineId; }
    public String getPurpose() { return purpose; }
    public String getAdditionalNotes() { return additionalNotes; }
    public UserId getRequestedBy() { return requestedBy; }
    public SupplierId getSuggestedSupplierId() { return suggestedSupplierId; }
    public List<MaterialRequestItem> getItems() { return new ArrayList<>(items); }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}