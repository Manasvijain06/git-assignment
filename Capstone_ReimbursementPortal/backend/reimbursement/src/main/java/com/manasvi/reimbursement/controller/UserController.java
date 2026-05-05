package com.manasvi.reimbursement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manasvi.reimbursement.dto.Request.LoginRequest;
import com.manasvi.reimbursement.dto.Request.UserRequest;
import com.manasvi.reimbursement.dto.Response.ApiResponse;
import com.manasvi.reimbursement.dto.Response.UserResponse;
import com.manasvi.reimbursement.entity.User;
import com.manasvi.reimbursement.service.UserService;

import jakarta.validation.Valid;

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
@CrossOrigin(origins = "*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Login a user
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> login(
            @RequestBody LoginRequest request) {

        logger.info("Login request received for email: {}", request.getEmail());

        UserResponse response = userService.login(request);

        logger.info("REST request to login user - COMPLETED");
        return ResponseEntity.ok(
                ApiResponse.success("Login successful", response));
    }

    /**
     * Creates a new user.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody UserRequest request) {

        logger.info("Create user request received for email: {}", request.getEmail());

        UserResponse response = userService.createUser(request);

        logger.info("REST request to create user - COMPLETED");
        return ResponseEntity.ok(
                ApiResponse.success("User created successfully", response));
    }

    /**
     * Get all users
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {

        logger.info("REST request to get all users - STARTED");

        List<UserResponse> users = userService.getAllUsers();

        logger.info("REST request to get all users - COMPLETED. Retrieved  records.");
        return ResponseEntity.ok(
                ApiResponse.success("Users fetched successfully", users));
    }

    /**
     * Get user by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        logger.info("Fetched user by ID: {} ", id);
        return ResponseEntity.ok(
                ApiResponse.success("User fetched successfully", userService.getUserById(id)));
    }

    /**
     * Delete a User
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {
        logger.info("REST request to delete user by ID: {} - STARTED", id);

        userService.deleteUser(id);

        logger.info("REST request to delete user by ID: {} - COMPLETED", id);

        return ResponseEntity.ok(
                ApiResponse.success("User deleted successfully", null));
    }

    /**
     * Assign manager
     */
    @PutMapping("/assign-manager")
    public ResponseEntity<ApiResponse<User>> assignManager(
            @RequestParam Long employeeId,
            @RequestParam Long managerId) {

        logger.info("Assign manager request: employeeId={}, managerId={}", employeeId, managerId);

        User updatedUser = userService.assignManager(employeeId, managerId);
        return ResponseEntity.ok(ApiResponse.success("Manager assigned", updatedUser));
    }

}
