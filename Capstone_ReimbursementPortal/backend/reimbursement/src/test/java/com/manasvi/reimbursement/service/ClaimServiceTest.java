package com.manasvi.reimbursement.service;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.manasvi.reimbursement.dto.Request.ClaimRequest;
import com.manasvi.reimbursement.entity.Claim;
import com.manasvi.reimbursement.entity.User;
import com.manasvi.reimbursement.enums.ClaimStatus;
import com.manasvi.reimbursement.enums.Role;
import com.manasvi.reimbursement.exception.ValidationException;
import com.manasvi.reimbursement.mapper.ClaimMapper;
import com.manasvi.reimbursement.repository.ClaimRepository;
import com.manasvi.reimbursement.repository.UserRepository;

class ClaimServiceTest {

    @Mock
    private ClaimRepository claimRepository;

    @Mock
    private UserRepository userRepository;

    private ClaimMapper claimMapper = new ClaimMapper();

    @InjectMocks
    private ClaimService claimService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        claimMapper = new ClaimMapper();
        claimService = new ClaimService(claimRepository, userRepository, claimMapper);
    }

    /**
     * CLAIM SUCCESS
     */
    @Test
    void createClaim_success() {

        ClaimRequest request = createRequest(1L, 500.0);

        User employee = createEmployee(1L);
        User manager = createManager(2L);

        employee.setManager(manager);

        Claim claim = new Claim();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(claimRepository.save(any()))
                .thenReturn(claim);

        Claim result = claimService.createClaim(request);

        assertNotNull(result);
        verify(claimRepository).save(any());
    }

    /**
     * INVALID AMOUNT
     */
    @Test
    void createClaim_invalidAmount() {

        ClaimRequest request = createRequest(1L, -100.0);
        User employee = createEmployee(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        assertThrows(ValidationException.class,
                () -> claimService.createClaim(request));
    }

    /**
     * NO ADMIN
     */
    @Test
    void createClaim_noAdmin() {

        ClaimRequest request = createRequest(1L, 500.0);
        User employee = createEmployee(1L);

        employee.setManager(null);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(userRepository.findByRole(Role.ADMIN))
                .thenReturn(Collections.emptyList());

        assertThrows(ValidationException.class,
                () -> claimService.createClaim(request));
    }

    /**
     * APPROVE UNAUTHORIZED
     */
    @Test
    void approveClaim_unauthorized() {

        Claim claim = new Claim();
        User reviewer = createUser(2L);

        claim.setReviewer(reviewer);
        claim.setStatus(ClaimStatus.SUBMITTED);

        when(claimRepository.findById(1L))
                .thenReturn(Optional.of(claim));

        assertThrows(RuntimeException.class,
                () -> claimService.approveClaim(1L, 99L, "ok"));
    }

    /**
     * APPROVE SUCCESS
     */
    @Test
    void approveClaim_success() {

        Claim claim = new Claim();
        User reviewer = createUser(2L);

        claim.setReviewer(reviewer);
        claim.setStatus(ClaimStatus.SUBMITTED);

        when(userRepository.findById(2L))
                .thenReturn(Optional.of(reviewer));

        when(claimRepository.findById(1L))
                .thenReturn(Optional.of(claim));

        claimService.approveClaim(1L, 2L, "Approved");

        assertEquals(ClaimStatus.APPROVED, claim.getStatus());
    }

    /**
     * HELPER METHODS
     */

    private ClaimRequest createRequest(Long empId, Double amount) {
        ClaimRequest req = new ClaimRequest();
        req.setEmployeeId(empId);
        req.setAmount(amount);
        return req;
    }

    private User createEmployee(Long id) {
        User user = new User();
        user.setId(id);
        user.setRole(Role.EMPLOYEE);
        return user;
    }

    private User createManager(Long id) {
        User user = new User();
        user.setId(id);
        user.setRole(Role.MANAGER);
        return user;
    }

    private User createUser(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }
}