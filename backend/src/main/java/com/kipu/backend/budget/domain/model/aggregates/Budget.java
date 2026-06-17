package com.kipu.backend.budget.domain.model.aggregates;

import com.kipu.backend.budget.domain.model.entities.ExpenseHistoryItem;
import com.kipu.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Budget extends AbstractDomainAggregateRoot<Budget> {
    private Long id;
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
    private List<ExpenseHistoryItem> expenseHistory;

    public Budget() {
        this.expenseHistory = new ArrayList<>();
        this.executed = 0.0;
        this.progress = 0.0;
        this.status = "Active";
    }

    public void addExpense(String concept, Double amount, String responsible, String description) {
        if (amount > this.available) {
            throw new IllegalStateException("budget.errors.insufficient-funds");
        }
        ExpenseHistoryItem item = new ExpenseHistoryItem(concept, amount, responsible, description);
        this.expenseHistory.add(item);
        this.executed += amount;
        this.available -= amount;
        recalculateMetrics();
    }

    public void addExtension(Double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Extension amount must be positive");
        this.budgeted += amount;
        this.available += amount;
        recalculateMetrics();
    }

    private void recalculateMetrics() {
        if (this.budgeted > 0) {
            this.progress = (this.executed / this.budgeted) * 100;
        }
        this.alert = (this.available < (this.budgeted * 0.1)) ? "LOW_FUNDS" : null;
    }
}