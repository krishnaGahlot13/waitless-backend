package com.waitless.backend.exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message){
        super(message);
    }
}