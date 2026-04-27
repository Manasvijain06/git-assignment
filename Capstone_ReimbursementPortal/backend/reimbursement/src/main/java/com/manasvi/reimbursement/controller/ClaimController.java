package com.manasvi.reimbursement.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.manasvi.reimbursement.entity.User;
import com.manasvi.reimbursement.entity.Claim;
import com.manasvi.reimbursement.mapper.ClaimMapper;
import com.manasvi.reimbursement.repository.UserRepository;
import com.manasvi.reimbursement.service.ClaimService;
import com.manasvi.reimbursement.dto.Request.ClaimRequest;
import com.manasvi.reimbursement.dto.Response.ClaimResponse;
import com.manasvi.reimbursement.dto.Response.ApiResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for claim APIs.
 */
@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    private static final Logger logger = LoggerFactory.getLogger(ClaimController.class);

    private final ClaimService claimService;
    private final ClaimMapper claimMapper;
    private final UserRepository userRepository;

    public ClaimController(ClaimService claimService,
        ClaimMapper claimMapper,UserRepository userRepository) {
        this.claimService = claimService;
        this.claimMapper = claimMapper;
        this.userRepository = userRepository;
    }

    /**
     * Create Claim
     */
    @PostMapping
public ResponseEntity<ApiResponse<ClaimResponse>> createClaim(
        @Valid @RequestBody ClaimRequest request) {

    logger.info("Employee {} submitting claim", request.getEmployeeId());

    // Fetch employee
    User employee = userRepository.findById(request.getEmployeeId())
            .orElseThrow(() -> new RuntimeException("Employee not found"));

    // Map request → entity
    Claim claim = claimMapper.toEntity(request, employee);

    // Save
    Claim savedClaim = claimService.createClaim(claim);

    ClaimResponse response = claimMapper.toResponse(savedClaim);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Claim submitted successfully", response));
}
    
    /**
     * Get Claim by ID
     */
    @GetMapping("/{id}")
    public Claim getById(@PathVariable Long id) {
        return claimService.getById(id);
    }

    /**
     * Get employee claim
     */
    @GetMapping("/employee/{employeeId}")
    public Page<Claim> getEmployeeClaims(
            @PathVariable Long employeeId,
            @RequestParam int page,
            @RequestParam int size) {

        return claimService.getClaimsByEmployee(employeeId, PageRequest.of(page, size));
    }

    /**
     * Get All claims
     */
    @GetMapping
    public Page<Claim> getAllClaims(
            @RequestParam int page,
            @RequestParam int size) {

        return claimService.getAllClaims(PageRequest.of(page, size));
    }

    /**
     * Approve Claim.
     */
    @PutMapping("/{id}/approve")
    public Claim approve(@PathVariable Long id,
                        @RequestParam Long reviewerId) {
        return claimService.approveClaim(id, reviewerId);
    }

    /**
     * Reject claim.
     */
    @PutMapping("/{id}/reject")
    public Claim reject(@PathVariable Long id,
                        @RequestParam Long reviewerId) {
        return claimService.rejectClaim(id, reviewerId);
    }
}