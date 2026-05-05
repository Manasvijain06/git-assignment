package com.manasvi.reimbursement.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExceptionTest {

    @Test
    void resourceNotFoundException_test() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Not found");
        assertEquals("Not found", ex.getMessage());
    }

    @Test
    void validationException_test() {
        ValidationException ex = new ValidationException("Invalid");
        assertEquals("Invalid", ex.getMessage());
    }
}