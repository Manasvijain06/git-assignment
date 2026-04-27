package com.manasvi.reimbursement.entity;

import java.time.LocalDate;
import com.manasvi.reimbursement.enums.ClaimStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDate claimDate;

    @Column(nullable = false, length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClaimStatus status = ClaimStatus.SUBMITTED;
    
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @Column(length = 1000)
    private String comment;

    // All getters

    public Long getId() {
        return id;
    }
    public User getEmployee() {
        return employee;
    }
    public User getReviewer() {
        return reviewer;
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

    //All setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setEmployee(User employee) {
        this.employee = employee;
    }
    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
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