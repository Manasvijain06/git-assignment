package com.Nucleusteq.SpringAndRestAssignment.exception;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message) {
        super(message);
    }
}
