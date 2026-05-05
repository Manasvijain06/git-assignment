package com.manasvi.reimbursement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.manasvi.reimbursement.dto.Request.ClaimRequest;
import com.manasvi.reimbursement.dto.Response.ClaimResponse;
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

    private static final Logger log = LoggerFactory.getLogger(ClaimService.class);

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
     * Create a claim
     */
    public ClaimResponse createClaim(ClaimRequest request) {

        log.debug("Creating claim for employeeId: {}", request.getEmployeeId());

        User employee = userRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (request.getAmount() == null || request.getAmount() <= 0) {
            throw new ValidationException("Amount exceeds allowed limit");
        }

        if (request.getDescription() == null || request.getDescription().isBlank()) {
            throw new ValidationException("Description is required");
        }

        // Explicitly set the relationships to prevent NullPointerExceptions during
        Claim claim = new Claim();
        claim.setAmount(request.getAmount());
        claim.setDescription(request.getDescription());
        claim.setClaimDate(request.getClaimDate());
        claim.setEmployee(employee);
        claim.setStatus(ClaimStatus.SUBMITTED);

        User reviewer;
        /**
         * manager approves
         */
        if (employee.getManager() != null) {
            reviewer = employee.getManager();
        }
        /**
         * fallback admin
         */
        else {
            reviewer = getAdminUser();
        }
        claim.setReviewer(reviewer);
        Claim saved = claimRepository.save(claim);
        return claimMapper.toResponse(saved);
    }

    private User getAdminUser() {
        return userRepository.findByRole(Role.ADMIN)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
    }

    /**
     * Get Claim by ID
     * 
     * @param id the claim ID
     * @return
     */
    public ClaimResponse getById(Long id) {
        return claimMapper.toResponse(
                claimRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Claim not found")));
    }

    /**
     * Get Claims by Employee
     * 
     * @param employeeId
     * @return
     */
    // EMPLOYEE CLAIMS
    public Page<ClaimResponse> getClaimsByEmployeeId(Long employeeId, Pageable pageable) {
        return claimRepository.findByEmployeeId(employeeId, pageable)
                .map(claimMapper::toResponse);
    }

    /**
     * Get all claims
     * 
     * @param pageable
     * @return
     */

    public Page<ClaimResponse> getAllClaims(Pageable pageable) {
        return claimRepository.findAll(pageable)
                .map(claimMapper::toResponse);
    }

    public Page<ClaimResponse> getClaimsByManager(Long managerId, Pageable pageable) {
        return claimRepository.findByEmployee_Manager_Id(managerId, pageable)
                .map(claimMapper::toResponse);
    }

    public List<ClaimResponse> getPendingClaimsByManager(Long managerId) {
        return claimRepository.findByEmployee_Manager_IdAndStatus(managerId, ClaimStatus.SUBMITTED)
                .stream()
                .map(claimMapper::toResponse)
                .toList();
    }

    /**
     * Retrieves all pending claims (SUBMITTED) and maps them to the response DTO
     */
    public List<ClaimResponse> getPendingClaims() {
        return claimRepository.findByStatus(ClaimStatus.SUBMITTED)
                .stream()
                .map(claimMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Take action (approve/reject) on a claim.
     * * @param claimId the claim ID.
     * 
     * @param comment the comment for the action.
     * @param status  the new claim status.
     * @return the updated ClaimResponse DTO.
     */
    public ClaimResponse takeActionClaim(Long claimId, String comment, ClaimStatus status) {

        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with ID: " + claimId));

        if (claim.getStatus() != ClaimStatus.SUBMITTED) {
            throw new ValidationException("Claim already processed");
        }
        if (claim.getReviewer() == null) {
            throw new ResourceNotFoundException("No reviewer assigned");
        }

        // rejection comment mandatory
        if (status == ClaimStatus.REJECTED &&
                (comment == null || comment.isBlank())) {
            throw new ValidationException("Comment required for rejection");
        }

        claim.setStatus(status);
        claim.setComment(comment);

        Claim saved = claimRepository.save(claim);

        return claimMapper.toResponse(saved);
    }

}