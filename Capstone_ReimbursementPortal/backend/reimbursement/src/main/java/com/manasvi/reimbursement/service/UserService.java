package com.manasvi.reimbursement.service;


import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.manasvi.reimbursement.dto.LoginRequest;
import com.manasvi.reimbursement.dto.UserRequest;
import com.manasvi.reimbursement.dto.UserResponse;
import com.manasvi.reimbursement.entity.User;
import com.manasvi.reimbursement.exception.BadRequestException;
import com.manasvi.reimbursement.exception.DuplicateResourceException;
import com.manasvi.reimbursement.exception.ResourceNotFoundException;
import com.manasvi.reimbursement.mapper.UserMapper;
import com.manasvi.reimbursement.repository.UserRepository;

/**
 * Service Layer for handling User related Business logic .
 * This class manages Operations such as :
 * Creating users
 * Fetching users (all and by ID)
 * Deleting users
 */

@Service
public class UserService {

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
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

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

    public UserResponse login(LoginRequest dto) {

    User user = userRepository.findByEmail(dto.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
        throw new BadRequestException("Invalid credentials");
    }

    return UserMapper.toResponse(user);
}

}
