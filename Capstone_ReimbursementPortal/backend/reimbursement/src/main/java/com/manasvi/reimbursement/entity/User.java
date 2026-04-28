package com.manasvi.reimbursement.entity;

import java.time.*;

import com.manasvi.reimbursement.enums.*;

import jakarta.persistence.*;

/**
 * Entity representing a system user.
 * A user can be:
 * - ADMIN
 * - MANAGER
 * - EMPLOYEE
 */

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public final void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters
    public final Long getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    public final String getEmail() {
        return email;
    }

    public final String getPassword() {
        return password;
    }

    public final Role getRole() {
        return role;
    }

    public User getManager() {
        return manager;
    }

    public final LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters
    public final void setId(final Long id) {
        this.id = id;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final void setEmail(final String email) {
        this.email = email;
    }

    public final void setPassword(final String password) {
        this.password = password;
    }

    public final void setRole(final Role role) {
        this.role = role;
    }

    public final void setManager(final User manager) {
        this.manager = manager;
    }

    public final void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
