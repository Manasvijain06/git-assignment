package com.manasvi.reimbursement.service;

import java.time.*;
import java.util.List;
import java.util.stream.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.manasvi.reimbursement.dto.Request.*;
import com.manasvi.reimbursement.dto.Response.UserResponse;
import com.manasvi.reimbursement.entity.User;
import com.manasvi.reimbursement.exception.ValidationException;
import com.manasvi.reimbursement.exception.ResourceNotFoundException;
import com.manasvi.reimbursement.mapper.UserMapper;
import com.manasvi.reimbursement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service Layer for handling User related Business logic .
 * This class manages Operations such as :
 * Creating users
 * Fetching users (all and by ID)
 * Deleting users
 */

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    /**
     * Constructor-based Dependency Injection
     * @param userRepository
     * @param passwordEncoder
     */

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create a new user in the system.
     * Validates that the email belongs to the allowed company domain
     * and that the email is not already registered.
     */
    public UserResponse createUser(UserRequest request) {
        logger.info("Creating user with email:{}",request.getEmail());

        if (!request.getEmail().endsWith("@company.com")) {
            throw new ValidationException("Invalid email domain. Use company email.");
}

        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new ValidationException("Email already registered: " + request.getEmail());
        }
    

        User user = UserMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        logger.info("User created with ID:{}",savedUser.getId());
        return UserMapper.toResponse(savedUser);
    }

    /**
     * Retrives all users in the system
     */

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
            .map(UserMapper::toResponse)
            .collect(Collectors.toList());
    }

    /**
     * Retrieves user by their ID.
     */

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID:" +id));
            return UserMapper.toResponse(user);
}
    /**
     * Delete the id by user
     */
    public void deleteUser(Long id) {
        logger.info("Deactivating user with ID:{]",id);

        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    // login
    public UserResponse login(LoginRequest dto) {

    User user = userRepository.findByEmail(dto.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ValidationException("Invalid credentials");
        }
        return UserMapper.toResponse(user);
}
}
