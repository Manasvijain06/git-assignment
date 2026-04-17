package com.Nucleusteq.SpringAdvance.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TodoRequestDTO {
    @NotNull
    @Size(min = 3)
    private String title;
    private String description;
    private String status;
    
    //All getters 
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getStatus() {
        return status;
    }

    //All setters
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
