package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.application.commandservices.MaterialCatalogCommandFailure;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCatalog;
import com.kipu.backend.shared.infrastructure.documentation.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.CREATED;

public class ResponseEntityFromMaterialCatalogCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<MaterialCatalog, MaterialCatalogCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                catalog -> new ResponseEntity<>(
                        MaterialCatalogResourceFromEntityAssembler.toResourceFromEntity(catalog),
                        CREATED),
                failure -> {
                    var status = statusFromFailure(failure);
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            localizedMessage(messageSource, failure.messageKey())));
                });
    }

    private static HttpStatus statusFromFailure(MaterialCatalogCommandFailure failure) {
        if (failure instanceof MaterialCatalogCommandFailure.Duplicate) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.BAD_REQUEST;
    }

    private static String localizedMessage(MessageSource messageSource, String messageKey) {
        return messageSource.getMessage(messageKey, null, messageKey, LocaleContextHolder.getLocale());
    }
}