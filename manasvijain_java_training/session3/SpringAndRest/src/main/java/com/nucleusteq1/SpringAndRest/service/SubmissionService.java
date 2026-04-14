package com.nucleusteq1.SpringAndRest.service;

import org.springframework.stereotype.Service;

import com.nucleusteq1.SpringAndRest.model.SubmissionRequest;

@Service
public class SubmissionService {
      public boolean validateSubmission(SubmissionRequest request) {
        // basic checks like null /empty values
        if (request == null) return false;

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            return false;
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            return false;
        }
        
        if (request.getAge() == null || request.getAge() <= 0) {
            return false;
        }

        return true;
    }
}


