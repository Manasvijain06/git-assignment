package com.manasvi.reimbursement.dto;
import jakarta.validation.constraints.*;

/**
 * DTO for User Creation Requests.
 */

public class UserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Role is required")
    private String role;

    private Long managerId;

    //All getters
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }
    public Long getManagerId() {
        return managerId;
    }
    //All setters
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }
    
}