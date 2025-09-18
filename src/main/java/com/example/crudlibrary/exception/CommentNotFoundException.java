package com.example.crudlibrary.exception;

public class CommentNotFoundException extends NotFoundException{
    public CommentNotFoundException(String message){
        super(message);
    }
}
