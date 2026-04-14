package com.nucleusteq1.SpringAndRest.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.nucleusteq1.SpringAndRest.model.User;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>();
    public UserRepository() { //dummy data
        users.add(new User(1, "Manasvi", 21, "Admin"));
        users.add(new User(2, "Anushka", 25, "User"));
        users.add(new User(3, "Dev", 28, "User"));
        users.add(new User(4, "Riya", 22, "Admin"));
        users.add(new User(5, "Amit", 30, "User"));
        users.add(new User(6, "Sneha", 27, "Admin"));
        users.add(new User(7, "Rahul", 24, "User"));

    }
    public List<User> searchUsers(String name,Integer age, String role) {
        return users.stream()
                .filter(user -> (name == null || user.getName().equalsIgnoreCase(name))) //case-insensitive search
                .filter(user -> (age == null || user.getAge() == age)) //exact match for age
                .filter(user -> (role == null || user.getRole().equalsIgnoreCase(role)))
                .collect(Collectors.toList());
     }

    public List<User> findAll() {
        return users;
    }
}