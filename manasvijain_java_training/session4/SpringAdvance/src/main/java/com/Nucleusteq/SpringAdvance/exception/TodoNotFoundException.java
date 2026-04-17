package com.Nucleusteq.SpringAdvance.exception;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(String msg) {
        super(msg);
    }
}
