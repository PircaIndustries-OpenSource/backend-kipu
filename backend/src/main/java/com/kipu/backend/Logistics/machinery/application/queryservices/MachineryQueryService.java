package com.kipu.backend.Logistics.machinery.application.queryservices;

import com.kipu.backend.Logistics.machinery.application.queries.GetMachineryByProjectIdQuery;
import com.kipu.backend.Logistics.machinery.application.queries.GetMachineryByIdQuery;
import com.kipu.backend.Logistics.machinery.domain.model.aggregates.Machinery;

import java.util.List;
import java.util.Optional;

public interface MachineryQueryService {

    List<Machinery> handle(GetMachineryByProjectIdQuery query);

    Optional<Machinery> handle(GetMachineryByIdQuery query);
}
