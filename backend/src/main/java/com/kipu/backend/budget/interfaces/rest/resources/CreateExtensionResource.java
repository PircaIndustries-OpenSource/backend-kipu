package com.kipu.backend.budget.interfaces.rest.resources;

public record CreateExtensionResource(
        Double amount,
        String reason,
        String responsible
) {}