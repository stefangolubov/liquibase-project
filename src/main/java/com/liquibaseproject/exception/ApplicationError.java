package com.liquibaseproject.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApplicationError {

    private Integer code;
    private String type;
    private String message;
}