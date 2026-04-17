package com.Nucleusteq.SpringAdvance.controller;

import java.util.List;

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
    private final TodoService todoservice;

    public TodoController(TodoService todoservice) {
        this.todoservice = todoservice;
    }
    

    @GetMapping
    public List<TodoResponseDTO> getAll() {
        return todoservice.getAll();
    }

    @GetMapping("/{id}")
    public TodoResponseDTO getById(@PathVariable Long id) {
        return todoservice.getById(id);
    } 

    @PostMapping
    public TodoResponseDTO createTodo(@RequestBody @Valid TodoRequestDTO dto) {
        return todoservice.createTodo(dto);
    }

    @PutMapping("/{id}")
    public TodoResponseDTO update(
        @PathVariable Long id,
        @Valid @RequestBody TodoRequestDTO dto) {
        return todoservice.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        todoservice.delete(id);
        return "Deleted successfully";
    }
}

