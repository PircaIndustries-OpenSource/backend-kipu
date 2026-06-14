package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCategory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseEntityFromMaterialCategoryQueryResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromMaterialCategory(MaterialCategory category) {
        return ResponseEntity.ok(MaterialCategoryResourceFromEntityAssembler.toResourceFromEntity(category));
    }

    public static ResponseEntity<?> toResponseEntityFromList(List<MaterialCategory> categories) {
        var resources = categories.stream()
                .map(MaterialCategoryResourceFromEntityAssembler::toResourceFromEntity)
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