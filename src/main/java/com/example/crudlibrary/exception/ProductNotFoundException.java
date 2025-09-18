package com.example.crudlibrary.exception;

public class ProductNotFoundException extends NotFoundException{
    public ProductNotFoundException(String message){
        super(message);
    }
}
