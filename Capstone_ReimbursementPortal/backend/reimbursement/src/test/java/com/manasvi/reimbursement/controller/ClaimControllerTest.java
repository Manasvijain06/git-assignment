package com.manasvi.reimbursement.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import com.manasvi.reimbursement.dto.Request.ClaimRequest;
import com.manasvi.reimbursement.dto.Response.ApiResponse;
import com.manasvi.reimbursement.dto.Response.ClaimResponse;
import com.manasvi.reimbursement.enums.ClaimStatus;
import com.manasvi.reimbursement.service.ClaimService;

@ExtendWith(MockitoExtension.class)
class ClaimControllerTest {

    @Mock
    private ClaimService claimService;

    @InjectMocks
    private ClaimController claimController;

    private ClaimRequest request;
    private ClaimResponse response;

    @BeforeEach
    void setUp() {
        request = new ClaimRequest();
        request.setEmployeeId(1L);
        request.setAmount(500.0);
        request.setDescription("Travel");

        response = new ClaimResponse();
        response.setId(1L);
        response.setAmount(500.0);
        response.setDescription("Travel");
        response.setStatus(ClaimStatus.SUBMITTED);
    }

    // ================= CREATE =================
    @Test
    void createClaim_test() {
        when(claimService.createClaim(any())).thenReturn(response);

        ResponseEntity<ApiResponse<ClaimResponse>> res = claimController.createClaim(request);

        assertNotNull(res.getBody());
        assertEquals("Claim submitted successfully", res.getBody().getMessage());
        verify(claimService).createClaim(any());
    }

    // ================= GET BY ID =================
    @Test
    void getById_test() {
        when(claimService.getById(1L)).thenReturn(response);

        ResponseEntity<ApiResponse<ClaimResponse>> res = claimController.getById(1L);

        assertEquals(1L, res.getBody().getData().getId());
    }

    // ================= GET ALL =================
    @Test
    void getAll_test() {
        Page<ClaimResponse> page = new PageImpl<>(List.of(response));

        when(claimService.getAllClaims(any())).thenReturn(page);

        ResponseEntity<ApiResponse<Page<ClaimResponse>>> res = claimController.getAllClaims(0, 10);

        assertEquals(1, res.getBody().getData().getTotalElements());
    }

    // ================= EMPLOYEE =================
    @Test
    void byEmployee_test() {
        Page<ClaimResponse> page = new PageImpl<>(List.of(response));

        when(claimService.getClaimsByEmployeeId(eq(1L), any()))
                .thenReturn(page);

        ResponseEntity<ApiResponse<Page<ClaimResponse>>> res = claimController.getClaims(1L, 0, 10);

        assertEquals(1, res.getBody().getData().getContent().size());
    }

    // ================= MANAGER =================
    @Test
    void byManager_test() {
        Page<ClaimResponse> page = new PageImpl<>(List.of(response));

        when(claimService.getClaimsByManager(eq(2L), any()))
                .thenReturn(page);

        ResponseEntity<ApiResponse<Page<ClaimResponse>>> res = claimController.getClaimsByManager(2L, 0, 10);

        assertNotNull(res.getBody());
    }

    // ================= PENDING =================
    @Test
    void pending_test() {
        when(claimService.getPendingClaims()).thenReturn(List.of(response));

        ResponseEntity<ApiResponse<List<ClaimResponse>>> res = claimController.getPendingClaims();

        assertEquals(1, res.getBody().getData().size());
    }

    // ================= MANAGER PENDING =================
    @Test
    void pendingManager_test() {
        when(claimService.getPendingClaimsByManager(2L))
                .thenReturn(List.of(response));

        ResponseEntity<ApiResponse<List<ClaimResponse>>> res = claimController.getPendingClaims(2L);

        assertEquals(1, res.getBody().getData().size());
    }

}