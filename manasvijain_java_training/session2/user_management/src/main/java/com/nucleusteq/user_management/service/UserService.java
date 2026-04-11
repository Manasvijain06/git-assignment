package com.nucleusteq.user_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nucleusteq.user_management.model.User;
import com.nucleusteq.user_management.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;

    // Constructor Injection (MANDATORY)
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    // Get all users
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    // Get user by ID
    public User getUserById(int id) {
        return repository.findById(id);
    }

    // Create user
    public User createUser(User user) {
        repository.save(user);
        return user;
    }
}