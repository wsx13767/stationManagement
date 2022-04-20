package com.example.stationManagement.exception;

import com.example.stationManagement.model.CommonResult;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResult> exception(Exception ex) {
        return ResponseEntity.internalServerError().body(new CommonResult(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResult> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for (FieldError error : result.getFieldErrors()) {
            sb.append(error.getDefaultMessage()).append(";");
        }
        return ResponseEntity.badRequest().body(new CommonResult(sb.toString()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResult> runtimeException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new CommonResult(ex.getMessage()));
    }

}
