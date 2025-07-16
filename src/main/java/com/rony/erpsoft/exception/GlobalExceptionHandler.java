package com.rony.erpsoft.exception;

import com.rony.erpsoft.configuration.AppResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<AppResponse<Object>> handleNotFound(ResourceNotFoundException ex) {
        AppResponse<Object> response = AppResponse
                .build(HttpStatus.NOT_FOUND)
                .message(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleJsonErrors(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid JSON input");
        error.put("message", ex.getMostSpecificCause().getMessage());

        return ResponseEntity.badRequest().body(error);
    }
}
