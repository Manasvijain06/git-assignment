package com.nucleusteq1.SpringAndRest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nucleusteq1.SpringAndRest.model.User;
import com.nucleusteq1.SpringAndRest.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    public List<User> searchUsers(String name, Integer age, String role) {
        if(name == null && age == null && role == null) {
            return repository.findAll();
        }
        return repository.searchUsers(name, age, role);
     }

}
