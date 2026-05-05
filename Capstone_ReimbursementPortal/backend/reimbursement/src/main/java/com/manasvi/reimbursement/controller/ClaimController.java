package com.manasvi.reimbursement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.manasvi.reimbursement.dto.Request.ClaimRequest;
import com.manasvi.reimbursement.dto.Request.ClaimActionRequest;
import com.manasvi.reimbursement.dto.Response.ApiResponse;
import com.manasvi.reimbursement.dto.Response.ClaimResponse;

import com.manasvi.reimbursement.enums.ClaimStatus;
import com.manasvi.reimbursement.exception.ValidationException;
import com.manasvi.reimbursement.service.ClaimService;

import jakarta.validation.Valid;

/**
 * Controller for claim APIs.
 */
@RestController
@RequestMapping("/api/claims")
@CrossOrigin(origins = "*")
public class ClaimController {
        private static final Logger logger = LoggerFactory.getLogger(ClaimController.class);

        private final ClaimService claimService;

        public ClaimController(ClaimService claimService) {
                this.claimService = claimService;
        }

        /**
         * Create a claim
         */
        @PostMapping
        public ResponseEntity<ApiResponse<ClaimResponse>> createClaim(
                        @Valid @RequestBody ClaimRequest request) {

                logger.info("Received request to create claim for employeeId: {}", request.getEmployeeId());

                ClaimResponse response = claimService.createClaim(request);
                logger.info("Claim created successfully with ID: {}", response.getId());
                return ResponseEntity.ok(
                                ApiResponse.success("Claim submitted successfully", response));
        }

        /**
         * Get Claim by ID
         */
        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<ClaimResponse>> getById(@PathVariable Long id) {
                logger.info("Fetching claim with ID: {}", id);

                return ResponseEntity.ok(
                                ApiResponse.success("Claim fetched successfully", claimService.getById(id)));
        }

        /**
         * Get All Claims
         */
        @GetMapping()
        public ResponseEntity<ApiResponse<Page<ClaimResponse>>> getAllClaims(
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "5") int size) {
                Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

                return ResponseEntity.ok(
                                ApiResponse.success("All claims fetched successfully",
                                                claimService.getAllClaims(pageable)));
        }

        /**
         * Get employee claim
         */
        @GetMapping("/employee/{id}")
        public ResponseEntity<ApiResponse<Page<ClaimResponse>>> getClaims(
                        @PathVariable Long id,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "5") int size) {

                Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

                return ResponseEntity.ok(
                                ApiResponse.success("All claim fetched Successfully",
                                                claimService.getClaimsByEmployeeId(id, pageable)));
        }

        /**
         * Fetch all pending claims for manager review
         */
        @GetMapping("/pending")
        public ResponseEntity<ApiResponse<List<ClaimResponse>>> getPendingClaims() {

                logger.info("Fetching pending claims for review");

                return ResponseEntity.ok(
                                ApiResponse.success("Pending claims",
                                                claimService.getPendingClaims()));
        }

        @GetMapping("/manager/{managerId}")
        public ResponseEntity<ApiResponse<Page<ClaimResponse>>> getClaimsByManager(
                        @PathVariable Long managerId,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "5") int size) {
                Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

                logger.info("Fetching claims for managerId: {}", managerId);

                return ResponseEntity.ok(ApiResponse.success("Pending claims fetched successfully",
                                claimService.getClaimsByManager(managerId, pageable)));
        }

        @GetMapping("/manager/{managerId}/pending")
        public ResponseEntity<ApiResponse<List<ClaimResponse>>> getPendingClaims(@PathVariable Long managerId) {
                return ResponseEntity.ok(
                                ApiResponse.success("Pending claims fetched successfully",
                                                claimService.getPendingClaimsByManager(managerId)));
        }

        @GetMapping("/admin/pending")
        public ResponseEntity<ApiResponse<List<ClaimResponse>>> getAllPendingForAdmin() {

                logger.info("Fetching all pending claims for admin");

                return ResponseEntity.ok(
                                ApiResponse.success("All pending claims for admin",
                                                claimService.getPendingClaims()));
        }

        /**
         * Approve / Reject Claim (Manager/Admin)
         */
        @PutMapping("/{id}/{action}")
        public ResponseEntity<ApiResponse<ClaimResponse>> takeActionOnClaim(
                        @PathVariable Long id, @PathVariable String action,
                        @Valid @RequestBody ClaimActionRequest request) {

                logger.info("Claim action request received: id={}, action={}", id, action);

                ClaimStatus status;

                if (action.equalsIgnoreCase("approve")) {
                        status = ClaimStatus.APPROVED;
                } else if (action.equalsIgnoreCase("reject")) {
                        status = ClaimStatus.REJECTED;
                } else {
                        throw new ValidationException("Invalid action: use approve or reject");
                }

                ClaimResponse response = claimService.takeActionClaim(
                                id, request.getComment(), status);

                return ResponseEntity.ok(
                                ApiResponse.success("Claim updated successfully", response));
        }

}