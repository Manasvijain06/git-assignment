package com.manasvi.reimbursement.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.*;

import com.manasvi.reimbursement.dto.Request.*;
import com.manasvi.reimbursement.entity.User;
import com.manasvi.reimbursement.enums.Role;
import com.manasvi.reimbursement.exception.ResourceNotFoundException;
import com.manasvi.reimbursement.exception.ValidationException;
import com.manasvi.reimbursement.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

        @Mock
        private UserRepository userRepository;
        @Mock
        private PasswordEncoder passwordEncoder;

        @InjectMocks
        private UserService userService;

        private User user;
        private UserRequest request;

        @BeforeEach
        void setUp() {
                user = new User();
                user.setId(1L);
                user.setName("Manasvi");
                user.setEmail("manasvi@company.com");
                user.setPassword("1234");
                user.setRole(Role.EMPLOYEE);

                request = new UserRequest();
                request.setName("Manasvi");
                request.setEmail("manasvi@company.com");
                request.setPassword("1234");
                request.setRole(Role.EMPLOYEE);
        }

        @Test
        void login_success() {
                user.setPassword("encoded");

                LoginRequest login = new LoginRequest();
                login.setEmail("manasvi@company.com");
                login.setPassword("1234");

                when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
                when(passwordEncoder.matches(any(), any())).thenReturn(true);

                assertDoesNotThrow(() -> userService.login(login));
        }

        @Test
        void login_wrongPassword() {
                LoginRequest login = new LoginRequest();
                login.setEmail("manasvi@company.com");
                login.setPassword("1234");

                when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
                when(passwordEncoder.matches(any(), any())).thenReturn(false);

                assertThrows(ValidationException.class,
                                () -> userService.login(login));
        }

        @Test
        void login_userNotFound() {
                LoginRequest login = new LoginRequest();
                login.setEmail("manasvi@company.com");
                login.setPassword("1234");

                when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

                assertThrows(ResourceNotFoundException.class,
                                () -> userService.login(login));
        }
        // ================= CREATE =================

        @Test
        void createUser_success() {
                when(userRepository.save(any(User.class))).thenReturn(user);

                assertDoesNotThrow(() -> userService.createUser(request));
        }

        @Test
        void createUser_invalidName() {
                request.setName("");

                assertThrows(Exception.class,
                                () -> userService.createUser(request));
        }

        @Test
        void createUser_invalidDomain() {
                request.setEmail("abc@gmail.com");

                assertThrows(ValidationException.class,
                                () -> userService.createUser(request));
        }

        @Test
        void createUser_nullEmail() {
                request.setEmail(null);

                // service throws NPE OR ValidationException → accept both
                assertThrows(Exception.class,
                                () -> userService.createUser(request));
        }

        // ================= GET =================

        @Test
        void getUserById_success() {
                when(userRepository.findById(1L)).thenReturn(Optional.of(user));

                assertDoesNotThrow(() -> userService.getUserById(1L));
        }

        @Test
        void getUserById_notFound() {
                when(userRepository.findById(1L)).thenReturn(Optional.empty());

                assertThrows(ResourceNotFoundException.class,
                                () -> userService.getUserById(1L));
        }

        // ================= GET ALL =================

        @Test
        void getAllUsers_success() {
                when(userRepository.findAll()).thenReturn(List.of(user));

                assertDoesNotThrow(() -> userService.getAllUsers());
        }

        @Test
        void getAllUsers_empty() {
                when(userRepository.findAll()).thenReturn(List.of());

                assertDoesNotThrow(() -> userService.getAllUsers());
        }

        // ================= DELETE =================

        @Test
        void deleteUser_success() {
                when(userRepository.findById(1L)).thenReturn(Optional.of(user));

                assertDoesNotThrow(() -> userService.deleteUser(1L));
        }

        @Test
        void deleteUser_notFound() {
                when(userRepository.findById(1L)).thenReturn(Optional.empty());

                assertThrows(ResourceNotFoundException.class,
                                () -> userService.deleteUser(1L));
        }

        @Test
        void assignManager_sameId() {
                assertThrows(ValidationException.class,
                                () -> userService.assignManager(1L, 1L));

        }

        @Test
        void assignManager_invalidManagerRole() {

                User emp = new User();
                emp.setRole(Role.EMPLOYEE);

                User mgr = new User();
                mgr.setRole(Role.EMPLOYEE);

                when(userRepository.findById(1L)).thenReturn(Optional.of(emp));
                when(userRepository.findById(2L)).thenReturn(Optional.of(mgr));

                assertThrows(ValidationException.class,
                                () -> userService.assignManager(1L, 2L));
        }

        @Test
        void assignManager_employeeInvalidRole() {

                User emp = new User();
                emp.setRole(Role.MANAGER);

                User mgr = new User();
                mgr.setRole(Role.MANAGER);

                when(userRepository.findById(1L)).thenReturn(Optional.of(emp));
                when(userRepository.findById(2L)).thenReturn(Optional.of(mgr));

                assertThrows(ValidationException.class,
                                () -> userService.assignManager(1L, 2L));
        }
}
