package com.example.crudlibrary.exception;

public class UserNotFoundException extends NotFoundException{
    public UserNotFoundException(String message){
        super(message);
    }
}
