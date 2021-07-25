package com.liammacpherson.api.util;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorResponseFactory errorResponseFactory;

    public RestExceptionHandler(ErrorResponseFactory errorResponseFactory) {
        this.errorResponseFactory = errorResponseFactory;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<?> handleEntityNotFoundException (
            EntityNotFoundException ex, WebRequest webRequest) {
        return buildResponseEntity(errorResponseFactory.getErrorResponse(ex, HttpStatus.NOT_FOUND, webRequest), HttpStatus.NOT_FOUND);
    }

    private static ResponseEntity<?> buildResponseEntity(final ErrorResponse apiError, final HttpStatus httpStatus) {
        return new ResponseEntity<>(apiError, httpStatus);
    }

}
