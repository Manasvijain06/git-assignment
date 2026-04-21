package com.manasvi.reimbursement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manasvi.reimbursement.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

}
