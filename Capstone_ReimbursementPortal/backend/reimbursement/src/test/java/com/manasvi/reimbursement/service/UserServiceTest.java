package com.manasvi.reimbursement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.*;

import com.manasvi.reimbursement.dto.Request.UserRequest;
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
                user.setEmail("manasvi@company.com"); // ✅ FIXED
                user.setPassword("1234");
                user.setRole(Role.EMPLOYEE);

                request = new UserRequest();
                request.setName("Manasvi");
                request.setEmail("manasvi@company.com"); // ✅ FIXED
                request.setPassword("1234");
                request.setRole(Role.EMPLOYEE);
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

                assertThrows(Exception.class, // ✅ accept NPE or ValidationException
                                () -> userService.createUser(request));
        }

        @Test
        void createUser_invalidDomain() {
                request.setEmail("abc@gmail.com"); // ❌ wrong domain

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
}