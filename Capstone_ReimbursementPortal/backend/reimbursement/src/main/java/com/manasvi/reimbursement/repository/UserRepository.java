package com.manasvi.reimbursement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manasvi.reimbursement.entity.User;
import com.manasvi.reimbursement.enums.Role;

/**
 * Repository interface for User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);

    List<User> findByManagerId(Long managerId);

    boolean existsByManagerId(Long managerId);

}
