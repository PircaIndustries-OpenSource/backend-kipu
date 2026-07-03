package com.kipu.backend.Logistics.machinery.interfaces.rest.transform;

import com.kipu.backend.Logistics.machinery.application.commandservices.MachineryCommandFailure;
import com.kipu.backend.Logistics.machinery.domain.model.aggregates.Machinery;
import com.kipu.backend.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.CREATED;

public class ResponseEntityFromMachineryCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<Machinery, MachineryCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                machinery -> new ResponseEntity<>(
                        MachineryResourceFromEntityAssembler.toResourceFromEntity(machinery),
                        CREATED),
                failure -> {
                    var status = statusFromFailure(failure);
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            localizedMessage(messageSource, failure.messageKey())));
                });
    }

    public static ResponseEntity<?> toResponseEntityFromUpdateResult(
            Result<Machinery, MachineryCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                machinery -> ResponseEntity.ok(
                        MachineryResourceFromEntityAssembler.toResourceFromEntity(machinery)),
                failure -> {
                    var status = statusFromFailure(failure);
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            localizedMessage(messageSource, failure.messageKey())));
                });
    }

    private static HttpStatus statusFromFailure(MachineryCommandFailure failure) {
        if (failure instanceof MachineryCommandFailure.NotFound) {
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.BAD_REQUEST;
    }

    private static String localizedMessage(MessageSource messageSource, String messageKey) {
        return messageSource.getMessage(messageKey, null, messageKey, LocaleContextHolder.getLocale());
    }
}
