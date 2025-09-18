package com.example.crudlibrary.exception;

public class VerifyCodeFailedException extends RuntimeException{
    public VerifyCodeFailedException(String message){
        super(message);
    }
}
