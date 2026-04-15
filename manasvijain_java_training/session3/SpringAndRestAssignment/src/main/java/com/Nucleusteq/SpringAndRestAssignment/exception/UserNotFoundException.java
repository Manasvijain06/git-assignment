package com.Nucleusteq.SpringAndRestAssignment.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }
}