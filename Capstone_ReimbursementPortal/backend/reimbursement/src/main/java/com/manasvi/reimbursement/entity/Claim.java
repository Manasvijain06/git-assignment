package com.manasvi.reimbursement.entity;

import java.time.LocalDate;

import com.manasvi.reimbursement.enums.ClaimStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;

@Entity
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDate claimDate;

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

    @PrePersist
    public void prePersist() {
        if (this.claimDate == null) {
            this.claimDate = LocalDate.now();
        }
    }

    public Claim() {
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

    public User getEmployee() {
        return employee;
    }

    public User getReviewer() {
        return reviewer;
    }

    public String getComment() {
        return comment;
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

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}