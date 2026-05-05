package com.manasvi.reimbursement.repository;

import java.util.List;

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

    List<Claim> findByReviewerId(Long reviewerId);

    List<Claim> findByStatus(ClaimStatus status);

    List<Claim> findByReviewerIdAndStatus(Long reviewerId, ClaimStatus status);

    Page<Claim> findByEmployee_Manager_Id(Long managerId, Pageable pageable);

    List<Claim> findByEmployee_Manager_IdAndStatus(Long managerId, ClaimStatus status);
}
