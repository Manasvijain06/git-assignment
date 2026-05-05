package com.manasvi.reimbursement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.manasvi.reimbursement.dto.Request.LoginRequest;
import com.manasvi.reimbursement.dto.Request.UserRequest;
import com.manasvi.reimbursement.dto.Response.UserResponse;
import com.manasvi.reimbursement.entity.User;
import com.manasvi.reimbursement.enums.Role;
import com.manasvi.reimbursement.exception.ResourceNotFoundException;
import com.manasvi.reimbursement.exception.ValidationException;
import com.manasvi.reimbursement.mapper.UserMapper;
import com.manasvi.reimbursement.repository.UserRepository;

import jakarta.transaction.*;

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
     * 
     * @param userRepository
     * @param passwordEncoder
     */

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Login
     * 
     * @param dto
     * @return
     */
    public UserResponse login(LoginRequest dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ValidationException("Invalid credentials");
        }
        return UserMapper.toResponse(user);
    }

    /**
     * Create a new user in the system.
     * Validates that the email belongs to the allowed company domain
     * and that the email is not already registered.
     */
    public UserResponse createUser(UserRequest request) {

        /**
         * Email domain validation
         */
        if (!request.getEmail().endsWith("@company.com")) {
            throw new ValidationException("Invalid email domain. Use company email.");
        }

        /**
         * check duplicate email
         */
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ValidationException("Email already registered: " + request.getEmail());
        }
        User user = UserMapper.toEntity(request);

        /**
         * Encrypt password
         */
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        logger.info("User created with ID:{}", savedUser.getId());

        return UserMapper.toResponse(savedUser);
    }

    /**
     * Retrieves all users in the system
     */

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves user by their ID.
     */

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID:" + id));
        return UserMapper.toResponse(user);
    }

    /**
     * Delete User
     */
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        userRepository.delete(user);
    }

    /**
     * Assign Manager
     * 
     * @param employeeId
     * @param managerId
     */
    @Transactional
    public User assignManager(Long employeeId, Long managerId) {
        if (employeeId.equals(managerId)) {
            throw new ValidationException("An employee cannot be assigned as their own manager.");
        }

        User employee = userRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));

        if (manager.getRole() != Role.MANAGER) {
            throw new ValidationException("Selected user is not a manager");
        }

        if (employee.getRole() != Role.EMPLOYEE) {
            throw new ValidationException("Only employee can be assigned manager");
        }

        employee.setManager(manager);
        return userRepository.save(employee);
    }

}
