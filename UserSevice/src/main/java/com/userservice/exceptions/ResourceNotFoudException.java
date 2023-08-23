package com.userservice.exceptions;

public class ResourceNotFoudException extends RuntimeException{

    public ResourceNotFoudException(){
        super("Resource not found n server!!");
    }

    public ResourceNotFoudException(String message){
        super(message);
    }
}
