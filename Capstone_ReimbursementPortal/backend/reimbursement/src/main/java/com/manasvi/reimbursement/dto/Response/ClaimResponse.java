package com.manasvi.reimbursement.dto.Response;

import java.time.LocalDate;

import com.manasvi.reimbursement.enums.ClaimStatus;

public class ClaimResponse {

    private Long id;
    private Double amount;
    private String description;
    private LocalDate claimDate;
    private ClaimStatus status;
    private String comment;
    private Long employeeId;
    private String employeeName;

    private Long reviewerId;
    private String reviewerName;

    public ClaimResponse() {

    }

    public Long getId() {
        return id;
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

    public ClaimStatus getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
}
