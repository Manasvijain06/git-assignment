package com.Nucleusteq.SpringAdvance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Nucleusteq.SpringAdvance.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
