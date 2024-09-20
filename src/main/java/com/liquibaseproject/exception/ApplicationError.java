package com.liquibaseproject.exception;

import lombok.Builder;

@Builder
public class ApplicationError {

    public Integer code;
    public String type;
    public String message;
}