package com.manasvi.reimbursement.mapper;

import org.springframework.stereotype.*;

import com.manasvi.reimbursement.dto.Request.ClaimRequest;
import com.manasvi.reimbursement.dto.Response.ClaimResponse;
import com.manasvi.reimbursement.entity.Claim;
import com.manasvi.reimbursement.enums.ClaimStatus;
import com.manasvi.reimbursement.entity.User;

/**
 * Mapper class responsible for converting between Claim entity and DTOs.
 */
@Component
public class ClaimMapper {
    /**
     * Converts a ClaimRequest DTO and employee User to a Claim entity.
     */

    public Claim toEntity(ClaimRequest request, User employee) {
        Claim claim = new Claim();
        claim.setEmployee(employee);
        claim.setAmount(request.getAmount());
        claim.setClaimDate(request.getClaimDate());
        claim.setDescription(request.getDescription());
        claim.setStatus(ClaimStatus.SUBMITTED);
        return claim;
    }

    /**
     * Converts a Claim entity to a ClaimResponse DTO.
     */

    public ClaimResponse toResponse(Claim claim) {
        ClaimResponse response = new ClaimResponse();
        response.setId(claim.getId());
        response.setAmount(claim.getAmount());
        response.setClaimDate(claim.getClaimDate());
        response.setDescription(claim.getDescription());
        response.setStatus(claim.getStatus());
        response.setComment(claim.getComment());

        if (claim.getEmployee() != null) {
            response.setEmployeeId(claim.getEmployee().getId());
        }
        return response;
    }
}
