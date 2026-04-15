package com.Nucleusteq.SpringAndRestAssignment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Nucleusteq.SpringAndRestAssignment.exception.InvalidUserException;
import com.Nucleusteq.SpringAndRestAssignment.exception.UserNotFoundException;
import com.Nucleusteq.SpringAndRestAssignment.model.User;
import com.Nucleusteq.SpringAndRestAssignment.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    public List<User> searchUsers(String name, Integer age, String role) {
        List<User> result;
        if (name == null && age == null && role == null) {
            result = repository.findAll();
        } else {
            result = repository.searchUsers(name, age, role);
        }

        if (result.isEmpty()) {
            throw new UserNotFoundException("No users found");
        }

        return result;
    }

     //DELETE (NEW API)
     
     public String deleteUser(int id, Boolean confirm) {    
        if (confirm == null || !confirm) {
            throw new InvalidUserException("Please confirm deletion");
        }

        boolean deleted = repository.deleteById(id);

        if (!deleted) {
            throw new UserNotFoundException("User not found with id " + id);
        }

        return "User deleted successfully";
    }

    // SUBMIT (NEW API)

public User submitUser(User user) {

    if (user.getName() == null || user.getName().isEmpty()) {
        throw new InvalidUserException("Invalid Input: Name is required");
    }

    if (user.getEmail() == null || user.getEmail().isEmpty()) {
        throw new InvalidUserException("Invalid Input: Email is required");
    }

    if (user.getAge() <= 0) {
        throw new InvalidUserException("Invalid Input: Age must be greater than 0");
    }

    // Check duplicate email
    if (repository.existsByEmail(user.getEmail())) {
        throw new InvalidUserException("Invalid Input: Email already exists");
    }

    // Save user
    repository.save(user);

    return user;
}
}
