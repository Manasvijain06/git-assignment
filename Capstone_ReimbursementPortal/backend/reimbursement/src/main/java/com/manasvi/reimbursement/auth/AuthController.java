package com.manasvi.reimbursement.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manasvi.reimbursement.dto.Request.LoginRequest;
import com.manasvi.reimbursement.dto.Response.UserResponse;

/**
 * Controller for handling authentication operations.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    /**
     * Constructs a new AuthController with required dependencies.
     *
     * @param authService the authentication service
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Authenticates the user and returns a token.
     *
     * @param request the login request
     * @return the response entity with the authentication result
     */
    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest request) {

        logger.info("Login request received for user: {}", request.getEmail());

        UserResponse response = authService.login(request);

        logger.info("Login request completed for user: {}", request.getEmail());

        return response;
    }
}