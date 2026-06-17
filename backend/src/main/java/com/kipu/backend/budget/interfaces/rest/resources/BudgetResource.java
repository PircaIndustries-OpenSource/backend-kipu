package com.kipu.backend.budget.interfaces.rest.resources;

import java.util.List;

public record BudgetResource(
        Long id,
        String projectId,
        Long progressId,
        String code,
        String name,
        String description,
        Double budgeted,
        Double executed,
        Double available,
        Double progress,
        String status,
        String alert,
        List<ExpenseItemResource> expenseHistory
) {
    public record ExpenseItemResource(
            Long id,
            String concept,
            Double amount,
            String responsible,
            String description,
            java.util.Date date
    ) {}
}