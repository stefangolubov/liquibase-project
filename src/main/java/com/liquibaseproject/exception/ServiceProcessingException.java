package com.liquibaseproject.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceProcessingException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1441201016393884947L;
    protected final Integer http;
    protected final Integer code;
    protected final String type;
    protected final String message;

    public ServiceProcessingException(
            Integer http, Integer code, String type, String message) {
        this.http = http;
        this.code = code;
        this.type = type;
        this.message = message;
    }
}
