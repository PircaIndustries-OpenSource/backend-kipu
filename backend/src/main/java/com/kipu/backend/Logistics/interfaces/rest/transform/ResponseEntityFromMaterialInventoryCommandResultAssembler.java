package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.application.commandservices.MaterialInventoryCommandFailure;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialInventory;
import com.kipu.backend.shared.infrastructure.documentation.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.CREATED;

public class ResponseEntityFromMaterialInventoryCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<MaterialInventory, MaterialInventoryCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                inventory -> new ResponseEntity<>(
                        MaterialInventoryResourceFromEntityAssembler.toResourceFromEntity(inventory),
                        CREATED),
                failure -> {
                    var status = statusFromFailure(failure);
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            localizedMessage(messageSource, failure.messageKey())));
                });
    }

    private static HttpStatus statusFromFailure(MaterialInventoryCommandFailure failure) {
        if (failure instanceof MaterialInventoryCommandFailure.Duplicate) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.BAD_REQUEST;
    }

    private static String localizedMessage(MessageSource messageSource, String messageKey) {
        return messageSource.getMessage(messageKey, null, messageKey, LocaleContextHolder.getLocale());
    }
}