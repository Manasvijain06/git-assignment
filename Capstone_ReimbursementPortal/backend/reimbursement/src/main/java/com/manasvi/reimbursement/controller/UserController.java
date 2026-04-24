package com.manasvi.reimbursement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.manasvi.reimbursement.dto.UserRequest;
import com.manasvi.reimbursement.dto.UserResponse;
import com.manasvi.reimbursement.dto.ApiResponse;
import com.manasvi.reimbursement.service.UserService;

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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET ALL USERS
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(
            new ApiResponse<>("Users fetched successfully", users));
        }
        
    // GET USER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(
            new ApiResponse<>("User fetched successfully", user));
        }

    // CREATE USER API
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody UserRequest dto) {

        UserResponse response = userService.createUser(dto);

        return ResponseEntity.ok(
                new ApiResponse<>("User created successfully", response)
        );
    }

    // DELETE USER API
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(
                new ApiResponse<>("User deleted successfully", null)
        );
    }
}