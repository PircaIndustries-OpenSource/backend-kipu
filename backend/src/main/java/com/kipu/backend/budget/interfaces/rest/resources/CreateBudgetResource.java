package com.kipu.backend.budget.interfaces.rest.resources;

public record CreateBudgetResource(
        String projectId,
        Long progressId,
        String code,
        String name,
        String description,
        Double budgeted
) {}
