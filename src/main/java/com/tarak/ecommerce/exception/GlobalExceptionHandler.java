package com.tarak.ecommerce.exception;

import com.tarak.ecommerce.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleDuplicate(
            ResourceAlreadyExistsException ex) {

        return new ResponseEntity<>(
                new ApiResponse("FAILED", ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse> handleInvalidCredentials(
            InvalidCredentialsException ex) {

        return new ResponseEntity<>(
                new ApiResponse("FAILED", ex.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }
}