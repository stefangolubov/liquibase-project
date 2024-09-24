package com.liquibaseproject.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.liquibaseproject.constant.ExceptionConstants.NO_ACCESS_TO_PERFORM_THIS_OPERATION_MESSAGE;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write("{\"code\": 403, \"type\": \"Forbidden\", \"message\": \"" + NO_ACCESS_TO_PERFORM_THIS_OPERATION_MESSAGE + "\"}");
    }
}