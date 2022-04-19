package com.example.stationManagement.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> exception(Exception ex) {
        return ResponseEntity.internalServerError().body(new Error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for (FieldError error : result.getFieldErrors()) {
            sb.append(error.getDefaultMessage()).append(";");
        }
        return ResponseEntity.badRequest().body(new Error(sb.toString()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Error> runtimeException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new Error(ex.getMessage()));
    }


    class Error {
        private String error;

        public Error(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
