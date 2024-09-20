package com.liquibaseproject.controller.advice;

import com.liquibaseproject.exception.ApplicationError;
import com.liquibaseproject.exception.LiquibaseProjectException;
import com.liquibaseproject.exception.ServiceProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {
    @ExceptionHandler
    public ResponseEntity<ApplicationError> handleSPE(ServiceProcessingException cause) {
        ApplicationError applicationError = ApplicationError.builder()
                .code(cause.getCode())
                .type(cause.getType())
                .message(cause.getMessage())
                .build();
        log.error("Business Exception with cause: ", cause);
        return ResponseEntity.status(cause.getHttp()).body(applicationError);
    }

    @ExceptionHandler
    public ResponseEntity<ApplicationError> handleUnhandled(Exception cause) {
        ApplicationError applicationError = ApplicationError.builder()
                .code(LiquibaseProjectException.INTERNAL_SERVER_ERROR.getCode())
                .type(LiquibaseProjectException.INTERNAL_SERVER_ERROR.getType())
                .message(LiquibaseProjectException.INTERNAL_SERVER_ERROR.getMessage())
                .build();
        log.error("Application Exception with cause: ", cause);
        return ResponseEntity.internalServerError().body(applicationError);
    }
}