package com.manasvi.reimbursement.dto.Request;


import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

/**
 * DTO for claim submission request.
 */

public class ClaimRequest {

    @NotNull(message = "EmployeeId is required")
    private Long employeeId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    @NotNull(message = "Claim date is required")
    private LocalDate claimDate;

    // Getters
    public Long getEmployeeId() {
        return employeeId;
    }
    public Double getAmount() {
        return amount;
    }
    public String getDescription() {
        return description;
    }
    public LocalDate getClaimDate() {
        return claimDate;
    }
    
    // Setters
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setClaimDate(LocalDate claimDate) {
        this.claimDate = claimDate;
    }


}

