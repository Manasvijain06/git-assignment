package com.manasvi.reimbursement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.manasvi.reimbursement.entity.Claim;
import com.manasvi.reimbursement.enums.ClaimStatus;

/**
 * Repository interface for Claim entity.
 */

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    Page<Claim> findByEmployeeId(Long employeeId, Pageable pageable);

    Page<Claim> findByReviewerId(Long reviewerId, Pageable pageable);

    Page<Claim> findByStatus(ClaimStatus status, Pageable pageable);

    Page<Claim> findByReviewerIdAndStatus(Long reviewerId, ClaimStatus status, Pageable pageable);

}
