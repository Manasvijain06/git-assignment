package com.Nucleusteq.SpringAdvance.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;
    public enum Status {
        PENDING,
        COMPLETED
    }
    public Todo() {}
    

    // All getters 
    public Long getId() {
        return id;
    }  
    public String getTitle() {
        return title;
    } 
    public String getDescription() {
        return description;
    }
    public Status getStatus() {
        return status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    } 

    //All setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
