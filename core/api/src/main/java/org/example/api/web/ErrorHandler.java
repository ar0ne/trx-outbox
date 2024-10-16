package org.example.api.web;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ErrorResponse;
import org.example.exception.OutboxException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(ConstraintViolationException exception) {
        log.error("Validation error", exception);
        String message = exception.getMessage();
        String code = String.valueOf(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorInfo = new ErrorResponse(code, message);
        return ResponseEntity.badRequest().body(errorInfo);
    }

    @ExceptionHandler(OutboxException.class)
    public ResponseEntity<ErrorResponse> handleException(OutboxException exception) {
        log.error("Inventory exception", exception);
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error("Unexpected exception", exception);
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }

}
