package com.Nucleusteq.SpringAdvance.controller;

import org.springframework.web.bind.annotation.*;

import com.Nucleusteq.SpringAdvance.dto.*;
import com.Nucleusteq.SpringAdvance.service.*;

import jakarta.validation.Valid;
import java.util.List;

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

