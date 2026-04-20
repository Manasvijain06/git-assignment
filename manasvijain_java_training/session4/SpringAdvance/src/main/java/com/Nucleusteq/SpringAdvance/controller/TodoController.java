package com.Nucleusteq.SpringAdvance.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Nucleusteq.SpringAdvance.dto.TodoRequestDTO;
import com.Nucleusteq.SpringAdvance.dto.TodoResponseDTO;
import com.Nucleusteq.SpringAdvance.service.TodoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
    private final TodoService todoservice;

    public TodoController(TodoService todoservice) {
        this.todoservice = todoservice;
    }
    

    @GetMapping
    public List<TodoResponseDTO> getAll() {
        logger.info("Fetching all todos");
        return todoservice.getAll();
    }

    @GetMapping("/{id}")
    public TodoResponseDTO getById(@PathVariable Long id) {
        logger.info("Fetching todo with id: {}", id);
        return todoservice.getById(id);
    } 

    @PostMapping
    public TodoResponseDTO createTodo(@RequestBody @Valid TodoRequestDTO dto) {
        logger.info("Creating new todo with title: {}", dto.getTitle());
        return todoservice.createTodo(dto);
    }

    @PutMapping("/{id}")
    public TodoResponseDTO update(
        @PathVariable Long id,@Valid @RequestBody TodoRequestDTO dto) {
            logger.info("Updating todo with id: {}", id);
            return todoservice.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        logger.info("Deleting todo with id: {}", id);
        todoservice.delete(id);
        return "Deleted successfully";
    }
}

