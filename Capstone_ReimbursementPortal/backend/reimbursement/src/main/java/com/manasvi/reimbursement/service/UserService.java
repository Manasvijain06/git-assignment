package com.manasvi.reimbursement.service;


import java.util.*;

import org.springframework.stereotype.*;

import com.manasvi.reimbursement.dto.*;
import com.manasvi.reimbursement.entity.*;
import com.manasvi.reimbursement.exception.*;
import com.manasvi.reimbursement.mapper.*;
import com.manasvi.reimbursement.repository.*;

/**
 * Service Layer for handling User related Business logic .
 * This class manages Operations such as :
 * Creating users
 * Fetching users (all and by ID)
 * Deleting users
 */

@Service
public class UserService {

    private final UserRepository userRepository;
    /**
     * Constructor-based Dependency Injection
     * @param userRepository
     */

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET ALL USERS
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
            .map(UserMapper::toResponse)
            .toList();
}

    // GET USER BY ID
    public UserResponse getUserById(Long id) {

    User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    return UserMapper.toResponse(user);
}

    // CREATE USER
    public UserResponse createUser(UserRequest dto) {

        // Email validation (domain check)
        if (!dto.getEmail().endsWith("@company.com")) {
            throw new BadRequestException("Invalid email domain. Use company email.");
}

        // Check if email already exists
        Optional<User> existing = userRepository.findByEmail(dto.getEmail());
        if (existing.isPresent()) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = UserMapper.toEntity(dto);

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    // DELETE USER
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }

        userRepository.deleteById(id);
    }

}
