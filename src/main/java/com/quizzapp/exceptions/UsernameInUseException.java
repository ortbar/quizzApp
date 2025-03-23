package com.quizzapp.exceptions;

public class UsernameInUseException extends RuntimeException{

    public UsernameInUseException(String message){
        super(message);
    }
}
