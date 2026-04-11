package com.nucleusteq.user_management.exception;

public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException(String message) {
        super(message);
    }
}