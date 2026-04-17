package com.Nucleusteq.SpringAdvance.dto;

public class TodoResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String createdAt;
    
    // ALl getters
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getStatus() {
        return status;
    }
    public String getCreatedAt() {
        return createdAt;
    }

    // ALl setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
