package com.manasvi.reimbursement.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DuplicateResourceExceptionTest {

    @Test
    void testConstructor() {
        DuplicateResourceException ex = new DuplicateResourceException("Duplicate data");

        assertEquals("Duplicate data", ex.getMessage());
    }
}