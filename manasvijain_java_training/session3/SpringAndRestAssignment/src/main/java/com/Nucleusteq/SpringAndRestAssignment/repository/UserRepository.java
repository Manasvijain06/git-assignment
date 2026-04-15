package com.Nucleusteq.SpringAndRestAssignment.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository; 

import com.Nucleusteq.SpringAndRestAssignment.model.User;

@Repository
public class UserRepository {

    private List<User> users = new ArrayList<>(); 

    public UserRepository() {// dummy data
        users.add(new User(1, "Manasvi", 21, "Admin", "manasvi@gmail.com"));
        users.add(new User(2, "Anushka", 25, "User", "anushka@gmail.com"));
        users.add(new User(3, "Dev", 28, "User", "dev@gmail.com"));
        users.add(new User(4, "Riya", 22, "Admin", "riya@gmail.com"));
        users.add(new User(5, "Amit", 30, "User", "amit@gmail.com"));
        users.add(new User(6, "Sneha", 27, "Admin","sneha@gmail.com"));
        users.add(new User(7, "Rahul", 24, "User", "rahul@gmail.com"));
    }

    // to Get all users
    public List<User> findAll() {
        return users;
    }

    // Search users
    public List<User> searchUsers(String name, Integer age, String role) {
        return users.stream()
                .filter(u -> name == null || u.getName().equalsIgnoreCase(name))
                .filter(u -> age == null || u.getAge() == age)
                .filter(u -> role == null || u.getRole().equalsIgnoreCase(role))
                .collect(Collectors.toList());
    }

    // Delete user by ID
    public boolean deleteById(int id) {
        return users.removeIf(u -> u.getId() == id);
    }

    // Save user
    public void save(User user) {
        users.add(user);
    }

    public boolean existsByEmail(String email) {
        return users.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }
}