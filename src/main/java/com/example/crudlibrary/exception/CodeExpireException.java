package com.example.crudlibrary.exception;

public class CodeExpireException extends RuntimeException{
    public CodeExpireException(String message){
        super(message);
    }
}
