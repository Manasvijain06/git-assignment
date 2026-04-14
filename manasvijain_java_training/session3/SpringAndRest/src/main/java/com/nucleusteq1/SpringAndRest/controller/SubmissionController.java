package com.nucleusteq1.SpringAndRest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nucleusteq1.SpringAndRest.model.SubmissionRequest;
import com.nucleusteq1.SpringAndRest.service.SubmissionService;

@RestController
public class SubmissionController {
    private final SubmissionService service;
    public SubmissionController(SubmissionService service) {
        this.service = service;
    }
     @PostMapping("/submit")
    public ResponseEntity<String> submitData(@RequestBody SubmissionRequest request) {

        boolean isValid = service.validateSubmission(request);

        if (!isValid) {// invalid input
            return new ResponseEntity<>("400 --> Invalid input", HttpStatus.BAD_REQUEST);
        }
        // success
        return new ResponseEntity<>("201 --> Submission successful", HttpStatus.CREATED);
    }
}



