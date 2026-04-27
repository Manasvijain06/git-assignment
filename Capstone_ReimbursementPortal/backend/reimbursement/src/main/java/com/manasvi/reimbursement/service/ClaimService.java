package com.manasvi.reimbursement.service;

import com.manasvi.reimbursement.entity.*;
import com.manasvi.reimbursement.enums.ClaimStatus;
import com.manasvi.reimbursement.exception.ValidationException;
import com.manasvi.reimbursement.repository.*;
import com.manasvi.reimbursement.exception.ResourceNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class handling all reimbursement claim operations including
 * submission, retrieval, pagination, and review (approval/rejection).
 */
@Service
public class ClaimService {

    private static final Logger logger = LoggerFactory.getLogger(ClaimService.class);
    private ClaimRepository claimRepository;
    private final UserRepository userRepository;

    public ClaimService(ClaimRepository claimRepository, UserRepository userRepository) {
        this.claimRepository = claimRepository;
        this.userRepository = userRepository;
    }

    /**
     * Submit a new claim
     */
    public Claim createClaim(Claim claim) {
        logger.info("Employee {} submitting claim for amount: {}",
        claim.getEmployee().getId(), claim.getAmount());
        claim.setStatus(ClaimStatus.SUBMITTED);
        return claimRepository.save(claim);
    }

    /**
     * Retrieve by ID
     */
    public Claim getById(Long id) {
        return claimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));
    }

    // EMPLOYEE CLAIM LIST (PAGINATED)
    public Page<Claim> getClaimsByEmployee(Long employeeId, Pageable pageable) {
        return claimRepository.findByEmployeeId(employeeId, pageable);
    }

    // ALL CLAIMS (MANAGER/ADMIN)
    public Page<Claim> getAllClaims(Pageable pageable) {
        return claimRepository.findAll(pageable);
    }

    // APPROVE CLAIM
    public Claim approveClaim(Long id, Long reviewerId) {
        Claim claim = getById(id);

        if (claim.getStatus() != ClaimStatus.SUBMITTED) {
            throw new ValidationException("Only submitted claims can be approved");
        }
        User reviewer = userRepository.findById(reviewerId)
            .orElseThrow(() -> new ResourceNotFoundException("Reviewer not found"));

    claim.setStatus(ClaimStatus.APPROVED);
    claim.setReviewer(reviewer);
    claim.setComment("Approved by reviewer");

    return claimRepository.save(claim);
}

    // REJECT CLAIM
    public Claim rejectClaim(Long id, Long reviewerId) {
        Claim claim = getById(id);

        if (claim.getStatus() != ClaimStatus.SUBMITTED) {
            throw new ValidationException("Only submitted claims can be rejected. Current status:" + claim.getStatus());
        }

        User reviewer = userRepository.findById(reviewerId)
            .orElseThrow(() -> new ResourceNotFoundException("Reviewer not found"));

    claim.setStatus(ClaimStatus.REJECTED);
    claim.setReviewer(reviewer);
    claim.setComment("Rejected by reviewer");

    return claimRepository.save(claim);
}
}