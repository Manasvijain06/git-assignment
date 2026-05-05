package com.manasvi.reimbursement.mapper;

import com.manasvi.reimbursement.dto.Response.ClaimResponse;
import com.manasvi.reimbursement.entity.Claim;
import com.manasvi.reimbursement.entity.User;
import com.manasvi.reimbursement.enums.ClaimStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClaimMapperTest {

    private final ClaimMapper mapper = new ClaimMapper();

    @Test
    void toResponse_success() {

        User employee = new User();
        employee.setId(1L);
        employee.setName("Test Employee");

        User reviewer = new User();
        reviewer.setId(2L);
        reviewer.setName("Manager");

        Claim claim = new Claim();
        claim.setId(10L);
        claim.setAmount(500.0);
        claim.setDescription("Travel");
        claim.setStatus(ClaimStatus.SUBMITTED);
        claim.setEmployee(employee);
        claim.setReviewer(reviewer);

        ClaimResponse response = mapper.toResponse(claim);

        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals("Travel", response.getDescription());
        assertEquals("Test Employee", response.getEmployeeName());
        assertEquals("Manager", response.getReviewerName());
    }

    @Test
    void toResponse_nullEmployee() {

        Claim claim = new Claim();
        claim.setId(1L);
        claim.setEmployee(null);

        ClaimResponse response = mapper.toResponse(claim);

        assertNotNull(response);
        assertNull(response.getEmployeeName());
    }

    @Test
    void toResponse_nullReviewer() {

        Claim claim = new Claim();
        claim.setId(1L);
        claim.setReviewer(null);

        ClaimResponse response = mapper.toResponse(claim);

        assertNull(response.getReviewerName());
    }

    @Test
    void mapper_nullFields() {

        Claim claim = new Claim();
        claim.setEmployee(null);
        claim.setReviewer(null);

        ClaimResponse res = new ClaimMapper().toResponse(claim);

        assertNotNull(res);
    }
}