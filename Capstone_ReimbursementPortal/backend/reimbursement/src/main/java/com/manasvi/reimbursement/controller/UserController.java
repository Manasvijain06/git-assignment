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
 *  Base Url: api/users
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
                UserResponse user = userService.createUser(request);
                return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("User created successfully", user));
            }

    /**
     * Retrieve all users
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", users));
        }
        
    /**
     * Retrieves a specific user by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success("User fetched successfully", user));
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
        logger.info("Request to remove User:{}",id);
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
            }

    // LOGIN API
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> login(
        @RequestBody LoginRequest dto) {
                UserResponse user = userService.login(dto);
                return ResponseEntity.ok(ApiResponse.success("Login successful", user));
    }
}