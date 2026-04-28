package com.manasvi.reimbursement.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.manasvi.reimbursement.dto.Request.ClaimRequest;
import com.manasvi.reimbursement.entity.Claim;
import com.manasvi.reimbursement.entity.User;
import com.manasvi.reimbursement.enums.ClaimStatus;
import com.manasvi.reimbursement.enums.Role;
import com.manasvi.reimbursement.exception.ResourceNotFoundException;
import com.manasvi.reimbursement.exception.ValidationException;
import com.manasvi.reimbursement.mapper.ClaimMapper;
import com.manasvi.reimbursement.repository.ClaimRepository;
import com.manasvi.reimbursement.repository.UserRepository;

/**
 * Service class handling all reimbursement claim operations including
 * submission, retrieval, pagination, and review (approval/rejection).
 */
@Service
public class ClaimService {

    private static final Logger logger = LoggerFactory.getLogger(ClaimService.class);

    private final ClaimRepository claimRepository;
    private final UserRepository userRepository;
    private final ClaimMapper claimMapper;

    public ClaimService(ClaimRepository claimRepository,
            UserRepository userRepository,
            ClaimMapper claimMapper) {
        this.claimRepository = claimRepository;
        this.userRepository = userRepository;
        this.claimMapper = claimMapper;
    }

    /**
     * Create a new claim
     */
    public Claim createClaim(ClaimRequest request) {

        logger.info("Employee {} submitting claim for amount: {}");
        /**
         * Fetch employee
         */
        User employee = userRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        /**
         * Amount must be positive
         */
        if (request.getAmount() <= 0) {
            throw new ValidationException("Amount must be greater than 0");
        }

        /**
         * Amount should be less than a limit
         */
        if (request.getAmount() > 10000) {
            throw new ValidationException("Amount exceeds allowed limit");
        }

        Claim claim = claimMapper.toEntity(request, employee);

        /**
         * Assign reviewer
         */
        User reviewer;

        if (employee.getManager() != null) {
            reviewer = employee.getManager();
        } else {
            List<User> admins = userRepository.findByRole(Role.ADMIN);

            if (admins.isEmpty()) {
                throw new ValidationException("No admin found");
            }
            reviewer = admins.get(0);
        }

        claim.setReviewer(reviewer);

        Claim savedClaim = claimRepository.save(claim);

        logger.info("Claim assigned to reviewer: {}", savedClaim.getId());

        return savedClaim;
    }

    /**
     * Get Claim by ID
     */
    public Claim getById(Long id) {
        logger.info("Fetching claim with ID: {}", id);
        return claimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));
    }

    /**
     * Get Claims by Employee
     */
    public Page<Claim> getClaimsByEmployee(Long employeeId, Pageable pageable) {
        return claimRepository.findByEmployeeId(employeeId, pageable);
    }

    /**
     * Get all claims (paginated)
     */
    public Page<Claim> getAllClaims(Pageable pageable) {

        logger.info("Fetching all claims");

        return claimRepository.findAll(pageable);
    }

    /*
     * Approve claim
     */
    public Claim approveClaim(Long id, Long reviewerId, String comment) {
        Claim claim = getById(id);

        if (claim.getStatus() != ClaimStatus.SUBMITTED) {
            throw new ValidationException("Only submitted claims can be approved");
        }
        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() -> new ResourceNotFoundException("Reviewer not found"));

        claim.setStatus(ClaimStatus.APPROVED);
        claim.setReviewer(reviewer);
        claim.setComment(comment);
        logger.info("Claim {} approved by reviewer {}", id, reviewerId);

        return claimRepository.save(claim);
    }

    // REJECT CLAIM
    public Claim rejectClaim(Long id, Long reviewerId, String comment) {
        Claim claim = getById(id);

        if (claim.getStatus() != ClaimStatus.SUBMITTED) {
            throw new ValidationException("Only submitted claims can be rejected. Current status:" + claim.getStatus());
        }

        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() -> new ResourceNotFoundException("Reviewer not found"));

        claim.setStatus(ClaimStatus.REJECTED);
        claim.setReviewer(reviewer);
        claim.setComment(comment);
        logger.info("Claim {} rejected by reviewer {}", id, reviewerId);

        return claimRepository.save(claim);
    }
}