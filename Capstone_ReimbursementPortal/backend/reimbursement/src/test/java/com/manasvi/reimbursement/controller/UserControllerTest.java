package com.manasvi.reimbursement.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;

import java.util.List;

import com.manasvi.reimbursement.dto.Request.LoginRequest;
import com.manasvi.reimbursement.dto.Request.UserRequest;
import com.manasvi.reimbursement.dto.Response.UserResponse;
import com.manasvi.reimbursement.entity.User;
import com.manasvi.reimbursement.enums.Role;
import com.manasvi.reimbursement.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserResponse response;
    private UserRequest request;
    private LoginRequest login;

    @BeforeEach
    void setUp() {
        response = new UserResponse();
        response.setId(1L);
        response.setName("Manasvi");

        request = new UserRequest();
        request.setName("Manasvi");
        request.setEmail("manasvi@company.com");
        request.setPassword("123");
        request.setRole(Role.EMPLOYEE);

        login = new LoginRequest();
        login.setEmail("manasvi@company.com");
        login.setPassword("123");
    }

    @Test
    void login_test() {
        when(userService.login(any())).thenReturn(response);

        ResponseEntity<?> res = userController.login(login);

        assertNotNull(res);
        verify(userService).login(any());
    }

    @Test
    void createUser_test() {
        when(userService.createUser(any())).thenReturn(response);

        ResponseEntity<?> res = userController.createUser(request);

        assertNotNull(res);
        verify(userService).createUser(any());
    }

    @Test
    void getAllUsers_test() {
        when(userService.getAllUsers()).thenReturn(List.of(response));

        ResponseEntity<?> res = userController.getAllUsers();

        assertNotNull(res);
        verify(userService).getAllUsers();
    }

    @Test
    void getById_test() {
        when(userService.getUserById(1L)).thenReturn(response);

        ResponseEntity<?> res = userController.getUserById(1L);

        assertNotNull(res);
        verify(userService).getUserById(1L);
    }

    @Test
    void delete_test() {
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<?> res = userController.deleteUser(1L);

        assertNotNull(res);
        verify(userService).deleteUser(1L);
    }

    @Test
    void assignManager_test() {
        User user = new User();
        user.setId(1L);

        when(userService.assignManager(1L, 2L)).thenReturn(user);

        ResponseEntity<?> res = userController.assignManager(1L, 2L);

        assertNotNull(res);
        verify(userService).assignManager(1L, 2L);
    }
}