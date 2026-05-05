package com.manasvi.reimbursement.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

    GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testResourceNotFound() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Not found");

        ResponseEntity<?> res = handler.handleResourceNotFound(ex);

        assertEquals(404, res.getStatusCode().value());
    }

    @Test
    void testDuplicate() {
        DuplicateResourceException ex = new DuplicateResourceException("Duplicate");

        ResponseEntity<?> res = handler.handleDuplicate(ex);

        assertEquals(409, res.getStatusCode().value());
    }

    @Test
    void testValidation() {
        ValidationException ex = new ValidationException("Invalid");

        ResponseEntity<?> res = handler.handleValidationException(ex);

        assertEquals(400, res.getStatusCode().value());
    }

    @Test
    void testGeneric() {
        Exception ex = new Exception("error");

        ResponseEntity<?> res = handler.handleGeneric(ex);

        assertEquals(500, res.getStatusCode().value());
    }
}