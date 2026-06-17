package com.kipu.backend.budget.interfaces.rest.resources;

public record CreateExpenseResource(
        String concept,
        Double amount,
        String responsible,
        String description
) {}