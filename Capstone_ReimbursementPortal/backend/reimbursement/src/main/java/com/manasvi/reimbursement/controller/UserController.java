package com.manasvi.reimbursement.controller;

import org.springframework.http.ResponseEntity;

import com.manasvi.reimbursement.dto.Response.UserResponse;
import com.manasvi.reimbursement.dto.Request.*;
import com.manasvi.reimbursement.dto.Response.ApiResponse;
import com.manasvi.reimbursement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Rest Controller for managing User related API endpoints.
 * This class defines endpoints for:
 * - Fetching all users
 * - Fetching a user by ID
 * - Creating a new user
 * - Deleting a user by ID
 * Base Url: api/users
 */

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody UserRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User created successfully",
                        userService.createUser(request)));
    }

    /**
     * Retrieve all users
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(
                ApiResponse.success("Users fetched successfully", userService.getAllUsers()));
    }

    /**
     * Retrieves a specific user by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("User fetched successfully", userService.getUserById(id)));
    }

    /**
     * To update a user
     */
    // In process ...

    /**
     * Deactivate a User
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {
        logger.info("Request to remove User:{}", id);
        userService.deleteUser(id);
        return ResponseEntity.ok(
                ApiResponse.success("User deleted successfully", null));
    }

    /**
     * Assign manager
     */
    @PutMapping("/{employeeId}/assign-manager/{managerId}")
    public ResponseEntity<ApiResponse<String>> assignManager(
            @PathVariable Long employeeId,
            @PathVariable Long managerId) {

        userService.assignManager(employeeId, managerId);
        return ResponseEntity.ok(ApiResponse.success("Manager assigned", null));
    }

    // LOGIN API
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> login(
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Login successful", userService.login(request)));
    }
}