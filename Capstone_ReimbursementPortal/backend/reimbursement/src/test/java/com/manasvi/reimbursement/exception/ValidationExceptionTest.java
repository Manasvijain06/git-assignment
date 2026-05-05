package com.manasvi.reimbursement.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ValidationExceptionTest {

    @Test
    void testConstructor() {
        ValidationException ex = new ValidationException("Invalid input");

        assertEquals("Invalid input", ex.getMessage());
    }
}