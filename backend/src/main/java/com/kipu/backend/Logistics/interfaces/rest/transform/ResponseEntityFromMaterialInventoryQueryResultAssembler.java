package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialInventory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseEntityFromMaterialInventoryQueryResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromMaterialInventory(MaterialInventory inventory) {
        return ResponseEntity.ok(MaterialInventoryResourceFromEntityAssembler.toResourceFromEntity(inventory));
    }

    public static ResponseEntity<?> toResponseEntityFromList(List<MaterialInventory> inventories) {
        var resources = inventories.stream()
                .map(MaterialInventoryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    public static ResponseEntity<ProblemDetail> badRequest(MessageSource messageSource, String messageKey, Object... args) {
        return problemDetail(HttpStatus.BAD_REQUEST, messageSource, messageKey, args);
    }

    public static ResponseEntity<ProblemDetail> notFound(MessageSource messageSource, String messageKey, Object... args) {
        return problemDetail(HttpStatus.NOT_FOUND, messageSource, messageKey, args);
    }

    private static ResponseEntity<ProblemDetail> problemDetail(HttpStatus status, MessageSource messageSource, String messageKey, Object... args) {
        var detail = messageSource.getMessage(messageKey, args, messageKey, LocaleContextHolder.getLocale());
        return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(status, detail));
    }
}