package com.nucleusteq.user_management.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nucleusteq.user_management.model.User;
import com.nucleusteq.user_management.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    // Constructor Injection
    public UserController(UserService service) {
        this.service = service;
    }

    // GET /users
    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    // GET /users/{id}
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return service.getUserById(id);
    }

    // POST /users
    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    
}