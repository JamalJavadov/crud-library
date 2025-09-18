package com.example.crudlibrary.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String message;
    private int status;
    private Instant timeStamp;
}
