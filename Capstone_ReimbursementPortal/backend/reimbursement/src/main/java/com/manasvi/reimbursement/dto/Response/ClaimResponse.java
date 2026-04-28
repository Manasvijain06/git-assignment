package com.manasvi.reimbursement.dto.Response;

import java.time.LocalDate;

import com.manasvi.reimbursement.enums.ClaimStatus;

public class ClaimResponse {

    private Long id;
    private Long employeeId;
    private Double amount;
    private LocalDate claimDate;
    private String description;
    private ClaimStatus status;
    private String comment;

    public ClaimResponse() {

    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDate getClaimDate() {
        return claimDate;
    }

    public String getDescription() {
        return description;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setClaimDate(LocalDate claimDate) {
        this.claimDate = claimDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
