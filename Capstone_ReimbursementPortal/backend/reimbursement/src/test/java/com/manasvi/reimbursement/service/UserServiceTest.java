package com.manasvi.reimbursement.service;

import com.manasvi.reimbursement.dto.Request.LoginRequest;
import com.manasvi.reimbursement.dto.Request.UserRequest;
import com.manasvi.reimbursement.entity.User;
import com.manasvi.reimbursement.enums.Role;
import com.manasvi.reimbursement.exception.ValidationException;
import com.manasvi.reimbursement.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * CREATE USER SUCCESS
     */
    @Test
    void createUser_success() {
        UserRequest request = createUserRequest();

        when(userRepository.findByEmail(any()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(any()))
                .thenReturn("encodedPass");

        when(userRepository.save(any()))
                .thenAnswer(i -> i.getArguments()[0]);

        var response = userService.createUser(request);

        assertEquals("Test", response.getName());
        assertEquals("test@company.com", response.getEmail());
        verify(userRepository).save(any());
    }

    /**
     * INVALID EMAIL DOMAIN
     */
    @Test
    void createUser_invalidDomain() {
        UserRequest request = createUserRequest();
        request.setEmail("wrong@gmail.com");

        assertThrows(ValidationException.class,
                () -> userService.createUser(request));
    }

    /**
     * DUPLICATE EMAIL
     */
    @Test
    void createUser_duplicateEmail() {
        UserRequest request = createUserRequest();

        when(userRepository.findByEmail(any()))
                .thenReturn(Optional.of(new User()));

        assertThrows(ValidationException.class,
                () -> userService.createUser(request));
    }

    /**
     * LOGIN SUCCESS
     */
    @Test
    void login_success() {
        User user = createUserEntity();

        when(userRepository.findByEmail(any()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(any(), any()))
                .thenReturn(true);

        var response = userService.login(
                new LoginRequest("test@company.com", "123456"));

        assertEquals("test@company.com", response.getEmail());
    }

    /**
     * LOGIN WRONG PASSWORD
     */
    @Test
    void login_wrongPassword() {
        User user = createUserEntity();

        when(userRepository.findByEmail(any()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(any(), any()))
                .thenReturn(false);

        assertThrows(ValidationException.class,
                () -> userService.login(
                        new LoginRequest("test@company.com", "wrong")));
    }

    /**
     *  HELPER METHODS
     */

    private UserRequest createUserRequest() {
        UserRequest req = new UserRequest();
        req.setName("Test");
        req.setEmail("test@company.com");
        req.setPassword("123456");
        req.setRole(Role.EMPLOYEE);
        return req;
    }

    private User createUserEntity() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@company.com");
        user.setPassword("encodedPass");
        user.setRole(Role.EMPLOYEE);
        return user;
    }
}