package com.Nucleusteq.SpringAndRestAssignment.model;

public class User {
    private int id;
    private String name;
    private int age;
    private String role;
    private String email;

    public User(int id, String name, int age, String role, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.role = role;
        this.email = email;
    }

    public User() {
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getRole() {
        return role;
    }
    public String getEmail() {
        return email;
    }
    
}



