package com.liquibaseproject.exception;

import com.liquibaseproject.constant.ExceptionConstants;
import com.liquibaseproject.model.ApiResponseSchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = ExceptionConstants.BAD_REQUEST,
                    content = @Content(mediaType = ExceptionConstants.APPLICATION_JSON,
                            schema = @Schema(implementation = ApiResponseSchema.class),
                            examples = {
                                    @ExampleObject(name = "NullPointerException", value = ExceptionConstants.NULL_POINTER_EXCEPTION_EXAMPLE),
                                    @ExampleObject(name = "EmptyInputException", value = ExceptionConstants.EMPTY_INPUT_EXCEPTION_EXAMPLE),
                                    @ExampleObject(name = "InvalidInputException", value = ExceptionConstants.INVALID_INPUT_EXCEPTION_EXAMPLE),
                                    @ExampleObject(name = "DataIntegrityViolationException", value = ExceptionConstants.DATA_INTEGRITY_VIOLATION_EXAMPLE)
                            }))
    })
    public ResponseEntity<ApiResponseSchema> handleNullPointerException(NullPointerException ex) {
        return new ResponseEntity<>(getApiResponseSchema(HttpStatus.BAD_REQUEST.value(), ExceptionConstants.NULL_POINTER_EXCEPTION_DESCRIPTION, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = ExceptionConstants.BAD_REQUEST,
                    content = @Content(mediaType = ExceptionConstants.APPLICATION_JSON,
                            schema = @Schema(implementation = ApiResponseSchema.class),
                            examples = {
                                    @ExampleObject(name = "NullPointerException", value = ExceptionConstants.NULL_POINTER_EXCEPTION_EXAMPLE),
                                    @ExampleObject(name = "EmptyInputException", value = ExceptionConstants.EMPTY_INPUT_EXCEPTION_EXAMPLE),
                                    @ExampleObject(name = "InvalidInputException", value = ExceptionConstants.INVALID_INPUT_EXCEPTION_EXAMPLE),
                                    @ExampleObject(name = "DataIntegrityViolationException", value = ExceptionConstants.DATA_INTEGRITY_VIOLATION_EXAMPLE)
                            }))
    })
    public ResponseEntity<ApiResponseSchema> handleEmptyInputException(EmptyInputException ex) {
        return new ResponseEntity<>(getApiResponseSchema(HttpStatus.BAD_REQUEST.value(), ExceptionConstants.EMPTY_INPUT_DESCRIPTION, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = ExceptionConstants.BAD_REQUEST,
                    content = @Content(mediaType = ExceptionConstants.APPLICATION_JSON,
                            schema = @Schema(implementation = ApiResponseSchema.class),
                            examples = {
                                    @ExampleObject(name = "NullPointerException", value = ExceptionConstants.NULL_POINTER_EXCEPTION_EXAMPLE),
                                    @ExampleObject(name = "EmptyInputException", value = ExceptionConstants.EMPTY_INPUT_EXCEPTION_EXAMPLE),
                                    @ExampleObject(name = "InvalidInputException", value = ExceptionConstants.INVALID_INPUT_EXCEPTION_EXAMPLE),
                                    @ExampleObject(name = "DataIntegrityViolationException", value = ExceptionConstants.DATA_INTEGRITY_VIOLATION_EXAMPLE)
                            }))
    })
    public ResponseEntity<ApiResponseSchema> handleInvalidInputException(InvalidInputException ex) {
        return new ResponseEntity<>(getApiResponseSchema(HttpStatus.BAD_REQUEST.value(), ExceptionConstants.INVALID_INPUT_DESCRIPTION, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = ExceptionConstants.BAD_REQUEST,
                    content = @Content(mediaType = ExceptionConstants.APPLICATION_JSON,
                            schema = @Schema(implementation = ApiResponseSchema.class),
                            examples = {
                                    @ExampleObject(name = "NullPointerException", value = ExceptionConstants.NULL_POINTER_EXCEPTION_EXAMPLE),
                                    @ExampleObject(name = "EmptyInputException", value = ExceptionConstants.EMPTY_INPUT_EXCEPTION_EXAMPLE),
                                    @ExampleObject(name = "InvalidInputException", value = ExceptionConstants.INVALID_INPUT_EXCEPTION_EXAMPLE),
                                    @ExampleObject(name = "DataIntegrityViolationException", value = ExceptionConstants.DATA_INTEGRITY_VIOLATION_EXAMPLE)
                            }))
    })
    public ResponseEntity<ApiResponseSchema> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(getApiResponseSchema(HttpStatus.BAD_REQUEST.value(), ExceptionConstants.DATA_INTEGRITY_VIOLATION_DESCRIPTION, ex.getMostSpecificCause().getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = ExceptionConstants.UNAUTHORIZED_DESCRIPTION,
                    content = @Content(mediaType = ExceptionConstants.APPLICATION_JSON,
                            schema = @Schema(implementation = ApiResponseSchema.class),
                            examples = @ExampleObject(value = ExceptionConstants.UNAUTHORIZED_EXCEPTION_EXAMPLE)))
    })
    public ResponseEntity<ApiResponseSchema> handleAuthenticationException(AuthenticationException ex) {
        return new ResponseEntity<>(getApiResponseSchema(HttpStatus.UNAUTHORIZED.value(), ExceptionConstants.UNAUTHORIZED_DESCRIPTION, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResultsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = ExceptionConstants.NOT_FOUND_DESCRIPTION,
                    content = @Content(mediaType = ExceptionConstants.APPLICATION_JSON,
                            schema = @Schema(implementation = ApiResponseSchema.class),
                            examples = @ExampleObject(value = ExceptionConstants.NOT_FOUND_EXCEPTION_EXAMPLE)))
    })
    public ResponseEntity<ApiResponseSchema> handleResultsNotFoundException(ResultsNotFoundException ex) {
        return new ResponseEntity<>(getApiResponseSchema(HttpStatus.NOT_FOUND.value(), ExceptionConstants.NOT_FOUND_DESCRIPTION, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = ExceptionConstants.INTERNAL_SERVER_ERROR_DESCRIPTION,
                    content = @Content(mediaType = ExceptionConstants.APPLICATION_JSON,
                            schema = @Schema(implementation = ApiResponseSchema.class),
                            examples = @ExampleObject(value = ExceptionConstants.INTERNAL_SERVER_ERROR_EXAMPLE)))
    })
    public ResponseEntity<ApiResponseSchema> handleInternalServerErrorException(InternalServerErrorException ex) {
        return new ResponseEntity<>(getApiResponseSchema(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionConstants.INTERNAL_SERVER_ERROR_DESCRIPTION, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = ExceptionConstants.INTERNAL_SERVER_ERROR_DESCRIPTION,
                    content = @Content(mediaType = ExceptionConstants.APPLICATION_JSON,
                            schema = @Schema(implementation = ApiResponseSchema.class),
                            examples = @ExampleObject(value = ExceptionConstants.INTERNAL_SERVER_ERROR_EXAMPLE)))
    })
    public ResponseEntity<ApiResponseSchema> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>(getApiResponseSchema(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionConstants.INTERNAL_SERVER_ERROR_DESCRIPTION, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ApiResponseSchema getApiResponseSchema(Integer code, String type, String message) {
        ApiResponseSchema response = new ApiResponseSchema();
        response.setCode(code);
        response.setType(type);
        response.setMessage(message);
        return response;
    }
}