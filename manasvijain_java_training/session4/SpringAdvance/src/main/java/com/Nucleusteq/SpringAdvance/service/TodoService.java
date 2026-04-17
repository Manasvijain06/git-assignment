package com.Nucleusteq.SpringAdvance.service;
import com.Nucleusteq.SpringAdvance.dto.*;
import org.springframework.stereotype.*;
import com.Nucleusteq.SpringAdvance.repository.TodoRepository;
import java.util.List;
import com.Nucleusteq.SpringAdvance.entity.Todo;
import com.Nucleusteq.SpringAdvance.entity.Todo.Status;
import com.Nucleusteq.SpringAdvance.exception.*;

import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    //Constructor injection
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // Create todo
    public TodoResponseDTO createTodo(TodoRequestDTO dto) {
        Todo todo = new Todo();
        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());

        if (dto.getStatus() != null) {
           try {
                todo.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new InvalidStatusTransitionException("Invalid status value");
            }
        }
        else {
            todo.setStatus(Status.PENDING);
        }
        // create time 
        todo.setCreatedAt(LocalDateTime.now());

        Todo savedTodo = todoRepository.save(todo);
        return convertToDTO(savedTodo);
    }

    // GET ALL Todos
    public List<TodoResponseDTO> getAll() {
        return todoRepository.findAll()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // GET Todos BY ID
    public TodoResponseDTO getById(Long id) {
        Todo todo = todoRepository.findById(id)
            .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        return convertToDTO(todo);
    }

    //  UPDATE
    public TodoResponseDTO update(Long id, TodoRequestDTO dto) {
        Todo existing = todoRepository.findById(id)
            .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());

        if (dto.getStatus() != null) {
            Status newStatus;
            try {
                newStatus = Status.valueOf(dto.getStatus().toUpperCase());
            } 
            catch (IllegalArgumentException e) {
                throw new InvalidStatusTransitionException("Invalid status value");
            }

           if ((existing.getStatus() == Status.PENDING && newStatus == Status.COMPLETED) ||
           (existing.getStatus() == Status.COMPLETED && newStatus == Status.PENDING)) {
            existing.setStatus(newStatus);
            }
            else {
               throw new InvalidStatusTransitionException("Invalid status transition");
            }
    }
    
        Todo updated = todoRepository.save(existing);
        return convertToDTO(updated);
    }

    // DELETE
    public void delete(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        todoRepository.delete(todo);
    }

    // MAPPING METHOD
    private TodoResponseDTO convertToDTO(Todo todo) {
        TodoResponseDTO dto = new TodoResponseDTO();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setDescription(todo.getDescription());
        dto.setStatus(todo.getStatus().name());
        dto.setCreatedAt(todo.getCreatedAt().toString());   
        return dto;
    }
}