package com.liquibaseproject.exception;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class ExceptionUtil {
    public ServiceProcessingException logAndBuildException(
            LiquibaseProjectException liquibaseProjectException) {
        log.error(getLogErrorMessage(liquibaseProjectException));
        return new ServiceProcessingException(
                liquibaseProjectException.getHttp(),
                liquibaseProjectException.getHttp(),
                liquibaseProjectException.getType(),
                liquibaseProjectException.getMessage());
    }

    private String getLogErrorMessage(LiquibaseProjectException liquibaseProjectException) {
        return liquibaseProjectException.getCode()
                + " - "
                + liquibaseProjectException.getMessage();
    }
}