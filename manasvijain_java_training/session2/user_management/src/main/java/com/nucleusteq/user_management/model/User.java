package com.nucleusteq.user_management.model;

public class User {

    private Integer id;
    private String name;
    private String email;

    // Default Constructor
    public User() {
    }

    // Parameterized Constructor
    public User(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    //  Setter for ID (REQUIRED for auto-generation)
    public void setId(Integer id) {
        this.id = id;
    }
}