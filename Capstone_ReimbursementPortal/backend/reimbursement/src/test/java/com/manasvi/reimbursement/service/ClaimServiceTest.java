package com.manasvi.reimbursement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
class ClaimServiceTest {

    @Mock
    private ClaimRepository claimRepository;

    @Mock
    private UserRepository userRepository;

    // Standard concrete instance. Bypasses Mockito's proxy/inlining issues.
    private final ClaimMapper claimMapper = new ClaimMapper();

    @InjectMocks
    private ClaimService claimService;

    private User employee;
    private User manager;
    private ClaimRequest claimRequest;
    private Claim claim;

    @BeforeEach
    void setUp() throws Exception {
        // Inject the mapper using reflection to avoid Mockito proxy constraints
        Field mapperField = ClaimService.class.getDeclaredField("claimMapper");
        mapperField.setAccessible(true);
        mapperField.set(claimService, claimMapper);

        employee = new User();
        employee.setId(1L);
        employee.setName("Manasvi Jain");
        employee.setRole(Role.EMPLOYEE);

        manager = new User();
        manager.setId(2L);
        manager.setName("Manan Jain");
        employee.setManager(manager);

        claimRequest = new ClaimRequest();
        claimRequest.setEmployeeId(1L);
        claimRequest.setAmount(500.0);
        claimRequest.setDescription("Travel expenses");
        claimRequest.setClaimDate(LocalDate.now());

        claim = new Claim();
        claim.setId(100L);
        claim.setAmount(500.0);
        claim.setDescription("Travel expenses");
        claim.setClaimDate(LocalDate.now());
        claim.setStatus(ClaimStatus.SUBMITTED);
        claim.setEmployee(employee);
        claim.setReviewer(manager);
    }

    @Test
    void createClaim_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(claimRepository.save(any(Claim.class))).thenReturn(claim);

        ClaimResponse response = claimService.createClaim(claimRequest);

        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals("Travel expenses", response.getDescription());
        verify(claimRepository, times(1)).save(any(Claim.class));
    }

    @Test
    void createClaim_EmployeeNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> claimService.createClaim(claimRequest));
    }

    @Test
    void createClaim_InvalidAmount() {
        claimRequest.setAmount(-10.0);
        when(userRepository.findById(1L)).thenReturn(Optional.of(employee));

        assertThrows(ValidationException.class, () -> claimService.createClaim(claimRequest));
    }

    @Test
    void createClaim_BlankDescription() {
        claimRequest.setDescription("");
        when(userRepository.findById(1L)).thenReturn(Optional.of(employee));

        assertThrows(ValidationException.class, () -> claimService.createClaim(claimRequest));
    }

    @Test
    void takeActionClaim_Success() {
        when(claimRepository.findById(100L)).thenReturn(Optional.of(claim));
        when(claimRepository.save(any(Claim.class))).thenReturn(claim);

        ClaimResponse response = claimService.takeActionClaim(100L, "Approved", ClaimStatus.APPROVED);

        assertNotNull(response);
        assertEquals(ClaimStatus.APPROVED, response.getStatus());
        verify(claimRepository, times(1)).save(claim);
    }

    @Test
    void takeActionClaim_AlreadyProcessed() {
        claim.setStatus(ClaimStatus.APPROVED);
        when(claimRepository.findById(100L)).thenReturn(Optional.of(claim));

        assertThrows(ValidationException.class,
                () -> claimService.takeActionClaim(100L, "Duplicate Action", ClaimStatus.APPROVED));
    }

    @Test
    void takeActionClaim_RejectionCommentRequired() {
        when(claimRepository.findById(100L)).thenReturn(Optional.of(claim));

        assertThrows(ValidationException.class, () -> claimService.takeActionClaim(100L, "", ClaimStatus.REJECTED));
    }
}