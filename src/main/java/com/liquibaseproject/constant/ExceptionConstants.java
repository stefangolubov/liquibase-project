package com.liquibaseproject.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ExceptionConstants {

    public static final String INTERNAL_SERVER_ERROR_DESCRIPTION = "Internal server error";
    public static final String NULL_POINTER_EXCEPTION_DESCRIPTION = "Null Pointer Exception";
    public static final String NOT_FOUND_DESCRIPTION = "Not Found";
    public static final String EMPTY_INPUT_DESCRIPTION = "Empty Input";
    public static final String INVALID_INPUT_DESCRIPTION = "Invalid input";
    public static final String DATA_INTEGRITY_VIOLATION_DESCRIPTION = "Data Integrity Violation";
    public static final String UNAUTHORIZED_DESCRIPTION = "Unauthorized";
    public static final String BAD_REQUEST = "Bad Request";
    public static final String APPLICATION_JSON = "application/json";

    public static final String BASE_JSON_EXAMPLE_START = "{\"code\": ";
    public static final String BASE_JSON_EXAMPLE_MIDDLE = ", \"type\": \"";
    public static final String BASE_JSON_EXAMPLE_END = "\", \"message\": \"";

    public static final String NULL_POINTER_EXCEPTION_EXAMPLE = BASE_JSON_EXAMPLE_START + 400 + BASE_JSON_EXAMPLE_MIDDLE + NULL_POINTER_EXCEPTION_DESCRIPTION + BASE_JSON_EXAMPLE_END + "A null pointer exception occurred.\"}";
    public static final String EMPTY_INPUT_EXCEPTION_EXAMPLE = BASE_JSON_EXAMPLE_START + 400 + BASE_JSON_EXAMPLE_MIDDLE + EMPTY_INPUT_DESCRIPTION + BASE_JSON_EXAMPLE_END + "Input cannot be empty.\"}";
    public static final String INVALID_INPUT_EXCEPTION_EXAMPLE = BASE_JSON_EXAMPLE_START + 400 + BASE_JSON_EXAMPLE_MIDDLE + INVALID_INPUT_DESCRIPTION + BASE_JSON_EXAMPLE_END + "The provided input is invalid.\"}";
    public static final String DATA_INTEGRITY_VIOLATION_EXAMPLE = BASE_JSON_EXAMPLE_START + 400 + BASE_JSON_EXAMPLE_MIDDLE + DATA_INTEGRITY_VIOLATION_DESCRIPTION + BASE_JSON_EXAMPLE_END + "The operation violates a data integrity constraint.\"}";
    public static final String UNAUTHORIZED_EXCEPTION_EXAMPLE = BASE_JSON_EXAMPLE_START + 401 + BASE_JSON_EXAMPLE_MIDDLE + UNAUTHORIZED_DESCRIPTION + BASE_JSON_EXAMPLE_END + "You are not authorized to access this resource.\"}";
    public static final String NOT_FOUND_EXCEPTION_EXAMPLE = BASE_JSON_EXAMPLE_START + 404 + BASE_JSON_EXAMPLE_MIDDLE + NOT_FOUND_DESCRIPTION + BASE_JSON_EXAMPLE_END + "The requested resource was not found.\"}";
    public static final String INTERNAL_SERVER_ERROR_EXAMPLE = BASE_JSON_EXAMPLE_START + 500 + BASE_JSON_EXAMPLE_MIDDLE + INTERNAL_SERVER_ERROR_DESCRIPTION + BASE_JSON_EXAMPLE_END + "An unexpected error occurred.\"}";
}