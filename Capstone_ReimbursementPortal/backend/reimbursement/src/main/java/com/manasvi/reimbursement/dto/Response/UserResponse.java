package com.manasvi.reimbursement.dto.Response;

/**
 * Response DTO for sending user data in api responses.
 */

public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String role;

    public UserResponse() {
    }

    // All getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    // All setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

}