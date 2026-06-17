package com.kipu.backend.budget.infrastructure.persistence.jpa.entities;

import com.kipu.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "budgets")
@Getter
@Setter
public class BudgetJpaEntity extends AuditableAbstractPersistenceEntity {


    @Column(nullable = false)
    private String projectId;

    private Long progressId;
    private String code;
    private String name;
    private String description;
    private Double budgeted;
    private Double executed;
    private Double available;
    private Double progress;
    private String status;
    private String alert;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "budget_id")
    private List<ExpenseHistoryJpaEntity> expenseHistory = new ArrayList<>();
}