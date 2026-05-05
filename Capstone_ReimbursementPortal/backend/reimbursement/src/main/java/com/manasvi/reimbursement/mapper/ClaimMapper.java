package com.manasvi.reimbursement.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.manasvi.reimbursement.dto.Request.ClaimRequest;
import com.manasvi.reimbursement.dto.Response.ClaimResponse;
import com.manasvi.reimbursement.entity.Claim;
import com.manasvi.reimbursement.entity.User;
import com.manasvi.reimbursement.enums.ClaimStatus;

/**
 * Mapper class responsible for converting between Claim entity and DTOs.
 */
@Component
public class ClaimMapper {
    /**
     * Converts a ClaimRequest DTO and employee User to a Claim entity.
     */

    public Claim toEntity(ClaimRequest request, User employee, User reviewer) {
        Claim claim = new Claim();

        claim.setAmount(request.getAmount());
        claim.setClaimDate(request.getClaimDate());
        claim.setDescription(request.getDescription());
        claim.setStatus(ClaimStatus.SUBMITTED);
        claim.setEmployee(employee);
        claim.setReviewer(reviewer);
        return claim;
    }

    /**
     * Converts a Claim entity to a ClaimResponse DTO.
     */

    public ClaimResponse toResponse(Claim claim) {
        ClaimResponse response = new ClaimResponse();
        response.setId(claim.getId());
        response.setAmount(claim.getAmount());
        response.setDescription(claim.getDescription());
        response.setClaimDate(claim.getClaimDate());
        response.setStatus(claim.getStatus());
        response.setComment(claim.getComment());

        if (claim.getEmployee() != null) {
            response.setEmployeeId(claim.getEmployee().getId());
            response.setEmployeeName(claim.getEmployee().getName());
        }
        if (claim.getReviewer() != null) {
            response.setReviewerId(claim.getReviewer().getId());
            response.setReviewerName(claim.getReviewer().getName());
        }
        return response;
    }
}
