package com.example.crudlibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundHandler(NotFoundException e){
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .timeStamp(Instant.now())
                .build();
        return new  ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CodeExpireException.class)
    public ResponseEntity<ErrorResponse> codeExpireHandler(CodeExpireException e){
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.REQUEST_TIMEOUT.value())
                .message(e.getMessage())
                .timeStamp(Instant.now())
                .build();
        return new  ResponseEntity<>(error,HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler(VerifyCodeFailedException.class)
    public ResponseEntity<ErrorResponse> verifyCodeFailedHandler(VerifyCodeFailedException e){
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .timeStamp(Instant.now())
                .build();
        return new  ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
