package com.rony.erpsoft.exception;

import com.rony.erpsoft.configuration.AppResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<AppResponse<Object>> handleNotFound(ResourceNotFoundException ex) {
        AppResponse<Object> response = AppResponse
                .build(HttpStatus.NOT_FOUND)
                .message(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
