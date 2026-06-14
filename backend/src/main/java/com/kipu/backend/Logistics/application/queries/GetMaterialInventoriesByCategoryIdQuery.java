package com.kipu.backend.Logistics.application.queries;

import com.kipu.backend.Logistics.domain.model.valueobjects.CategoryId;

public record GetMaterialInventoriesByCategoryIdQuery(CategoryId categoryId) {
    public GetMaterialInventoriesByCategoryIdQuery {
        if (categoryId == null)
            throw new IllegalArgumentException("categoryId cannot be null");
    }
}