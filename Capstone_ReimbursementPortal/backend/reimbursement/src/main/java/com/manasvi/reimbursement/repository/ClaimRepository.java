package com.manasvi.reimbursement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manasvi.reimbursement.entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Long> {

}
