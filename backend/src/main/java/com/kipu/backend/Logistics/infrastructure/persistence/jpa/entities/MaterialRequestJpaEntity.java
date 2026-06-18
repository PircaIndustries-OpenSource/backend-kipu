package com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities;

import com.kipu.backend.Logistics.domain.model.valueobjects.RequestPriority;
import com.kipu.backend.Logistics.domain.model.valueobjects.RequestStatus;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.BudgetLineId;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.UserId;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.BudgetLineIdAttributeConverter;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.RequestPriorityAttributeConverter;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.RequestStatusAttributeConverter;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.converters.UserIdAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "material_request")
public class MaterialRequestJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deadline", nullable = false)
    private Instant deadline;

    @Column(name = "request_status", nullable = false)
    @Convert(converter = RequestStatusAttributeConverter.class)
    private RequestStatus requestStatus;

    @Column(name = "request_priority", nullable = false)
    @Convert(converter = RequestPriorityAttributeConverter.class)
    private RequestPriority requestPriority;

    @Column(name = "delivery_location", nullable = false, length = 255)
    private String deliveryLocation;

    @Column(name = "budget_line_id")
    @Convert(converter = BudgetLineIdAttributeConverter.class)
    private BudgetLineId budgetLineId;

    @Column(name = "purpose", columnDefinition = "TEXT")
    private String purpose;

    @Column(name = "additional_notes", columnDefinition = "TEXT")
    private String additionalNotes;

    @Column(name = "requested_by", nullable = false)
    @Convert(converter = UserIdAttributeConverter.class)
    private UserId requestedBy;

    @OneToMany(mappedBy = "materialRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaterialRequestItemJpaEntity> items = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    public MaterialRequestJpaEntity(Long id, Instant deadline, RequestStatus requestStatus,
                                    RequestPriority requestPriority, String deliveryLocation,
                                    BudgetLineId budgetLineId, String purpose, String additionalNotes,
                                    UserId requestedBy, List<MaterialRequestItemJpaEntity> items,
                                    Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.deadline = deadline;
        this.requestStatus = requestStatus;
        this.requestPriority = requestPriority;
        this.deliveryLocation = deliveryLocation;
        this.budgetLineId = budgetLineId;
        this.purpose = purpose;
        this.additionalNotes = additionalNotes;
        this.requestedBy = requestedBy;
        this.items = items != null ? items : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}