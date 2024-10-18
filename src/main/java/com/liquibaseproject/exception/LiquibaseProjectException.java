package com.liquibaseproject.exception;

import com.fasterxml.jackson.annotation.JsonValue;
import com.liquibaseproject.constant.ExceptionConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LiquibaseProjectException {
    MANDATORY_USERNAME_AND_E_MAIL(400, 400, ErrorType.INVALID_INPUT.getDisplayName(), ExceptionConstants.USERNAME_AND_E_MAIL_ARE_MANDATORY),
    MANDATORY_USERID_PRODUCT_ID_AND_QUANTITY(400, 400, ErrorType.INVALID_INPUT.getDisplayName(), ExceptionConstants.USER_ID_PRODUCT_ID_AND_QUANTITY_ARE_MANDATORY_MESSAGE),
    MANDATORY_NAME_PRICE_AND_QUANTITY(400, 400, ErrorType.INVALID_INPUT.getDisplayName(), ExceptionConstants.NAME_PRICE_AND_QUANTITY_ARE_MANDATORY),
    DATA_INTEGRITY_VIOLATION_PRODUCTS(400, 400, ErrorType.DATA_INTEGRITY_VIOLATION.getDisplayName(), ExceptionConstants.DATA_INTEGRITY_VIOLATION_PRODUCT_MESSAGE),
    DATA_INTEGRITY_VIOLATION_USERS(400, 400, ErrorType.DATA_INTEGRITY_VIOLATION.getDisplayName(), ExceptionConstants.DATA_INTEGRITY_VIOLATION_USER_MESSAGE),

    USERS_NOT_FOUND_EXCEPTION(404, 404, ErrorType.NOT_FOUND.getDisplayName(), ExceptionConstants.USERS_NOT_FOUND_EXCEPTION_MESSAGE),
    ORDERS_NOT_FOUND_EXCEPTION(404, 404, ErrorType.NOT_FOUND.getDisplayName(), ExceptionConstants.ORDERS_NOT_FOUND_EXCEPTION_MESSAGE),
    PRODUCTS_NOT_FOUND_EXCEPTION(404, 404, ErrorType.NOT_FOUND.getDisplayName(), ExceptionConstants.PRODUCTS_NOT_FOUND_EXCEPTION_MESSAGE),

    INTERNAL_SERVER_ERROR(500, 500, ErrorType.INTERNAL_SERVER_ERROR.getDisplayName(), ExceptionConstants.INTERNAL_SERVER_ERROR_MESSAGE);;

    private final Integer http;
    private final Integer code;
    private final String type;
    private final String message;

    public enum ErrorType {
        INVALID_INPUT("Invalid input"),
        NOT_FOUND("Not Found"),
        DATA_INTEGRITY_VIOLATION("Data Integrity Violation"),
        INTERNAL_SERVER_ERROR("Internal Server Error");

        private final String displayName;

        ErrorType(String displayName) {
            this.displayName = displayName;
        }

        @JsonValue
        public String getDisplayName() {
            return displayName;
        }
    }
}