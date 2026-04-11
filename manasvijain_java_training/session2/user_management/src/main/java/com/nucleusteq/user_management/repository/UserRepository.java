package com.nucleusteq.user_management.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.nucleusteq.user_management.model.User;

@Repository
public class UserRepository {

    private List<User> users = new ArrayList<>();
    
    //dummy data for testing
    public UserRepository() {
        users.add(new User(1, "Manasvi", "manasvi@example.com"));
        users.add(new User(2, "Rishi", "rishi@example.com"));
        users.add(new User(3, "Prisha", "prisha@example.com"));
    }


    // Get all users
    public List<User> findAll() {
        return users;
    }

    // Get user by ID
    

public Optional<User> findById(int id) {
    return users.stream()
            .filter(user -> user.getId() == id)
            .findFirst();
}


    // Save user
    public void save(User user) {
        users.add(user);
    }
    public boolean existsById(int id) {
    return users.stream().anyMatch(user -> user.getId() == id);
}

    
}