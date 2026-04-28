package com.manasvi.reimbursement.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        public ClaimController(ClaimService claimService,
                        ClaimMapper claimMapper) {
                this.claimService = claimService;
                this.claimMapper = claimMapper;
        }

        /**
         * Create Claim
         */
        @PostMapping
        public ResponseEntity<ApiResponse<ClaimResponse>> createClaim(
                        @Valid @RequestBody ClaimRequest request) {

                logger.info("Received request to create claim for employeeId: {}", request.getEmployeeId());
                Claim claim = claimService.createClaim(request);
                ClaimResponse response = claimMapper.toResponse(claim);

                logger.info("Claim created successfully with ID: {}", claim.getId());
                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(ApiResponse.success("Claim submitted successfully", response));
        }

        /**
         * Get Claim by ID
         */
        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<ClaimResponse>> getById(@PathVariable Long id) {
                logger.info("Fetching claim with ID: {}", id);

                Claim claim = claimService.getById(id);
                ClaimResponse response = claimMapper.toResponse(claim);

                return ResponseEntity.ok(
                                ApiResponse.success("Claim fetched successfully", response));
        }

        /**
         * Get employee claim
         */
        @GetMapping("/employee/{employeeId}")
        public Page<Claim> getEmployeeClaims(
                        @PathVariable Long employeeId,
                        @RequestParam int page,
                        @RequestParam int size) {

                Page<Claim> claims = claimService.getClaimsByEmployee(employeeId, PageRequest.of(page, size));
                return claimService.getClaimsByEmployee(employeeId, PageRequest.of(page, size));
        }

        /**
         * Get All Claims
         */
        @GetMapping
        public ResponseEntity<ApiResponse<Page<ClaimResponse>>> getAllClaims(
                        @RequestParam int page,
                        @RequestParam int size) {
                logger.info("Fetching all claims, page: {}, size: {}", page, size);

                Page<Claim> claims = claimService.getAllClaims(
                                PageRequest.of(page, size));

                Page<ClaimResponse> response = claims.map(claimMapper::toResponse);

                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "All claims fetched successfully", response));
        }

        /**
         * Approve Claim.
         */
        @PutMapping("/{id}/approve")
        public ResponseEntity<ApiResponse<ClaimResponse>> approve(
                        @PathVariable Long id,
                        @RequestParam Long reviewerId,
                        @RequestParam String comment) {

                logger.info("Approving claim ID: {} by reviewer: {}", id, reviewerId);

                Claim claim = claimService.approveClaim(id, reviewerId, comment);
                ClaimResponse response = claimMapper.toResponse(claim);

                logger.info("Claim ID: {} approved successfully", id);

                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Claim approved successfully", response));
        }

        /**
         * Reject claim.
         */
        @PutMapping("/{id}/reject")
        public ResponseEntity<ApiResponse<ClaimResponse>> reject(
                        @PathVariable Long id,
                        @RequestParam Long reviewerId,
                        @RequestParam String comment) {
                logger.info("Rejecting claim ID: {} by reviewer: {}", id, reviewerId);
                Claim claim = claimService.rejectClaim(id, reviewerId, comment);
                ClaimResponse response = claimMapper.toResponse(claim);

                logger.info("Claim ID: {} rejected successfully", id);

                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Claim rejected successfully", response));
        }
}