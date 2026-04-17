package com.Nucleusteq.SpringAdvance.exception;

public class InvalidStatusTransitionException extends RuntimeException {
    public InvalidStatusTransitionException(String msg) {
        super(msg);
    }
}
