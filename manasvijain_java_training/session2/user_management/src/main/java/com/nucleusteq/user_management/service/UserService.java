package com.nucleusteq.user_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nucleusteq.user_management.exception.DuplicateUserException;
import com.nucleusteq.user_management.exception.UserNotFoundException;
import com.nucleusteq.user_management.model.User;
import com.nucleusteq.user_management.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
    }

    public User createUser(User user) {

        if (repository.existsById(user.getId())) {
            throw new DuplicateUserException("User with ID " + user.getId() + " already exists");
        }
        repository.save(user);

        return user; 
    }
}