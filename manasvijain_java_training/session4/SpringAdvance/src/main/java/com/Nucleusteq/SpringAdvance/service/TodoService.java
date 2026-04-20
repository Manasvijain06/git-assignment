package com.Nucleusteq.SpringAdvance.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.Nucleusteq.SpringAdvance.dto.TodoRequestDTO;
import com.Nucleusteq.SpringAdvance.dto.TodoResponseDTO;
import com.Nucleusteq.SpringAdvance.entity.Todo;
import com.Nucleusteq.SpringAdvance.entity.Todo.Status;
import com.Nucleusteq.SpringAdvance.exception.InvalidStatusTransitionException;
import com.Nucleusteq.SpringAdvance.exception.TodoNotFoundException;
import com.Nucleusteq.SpringAdvance.repository.TodoRepository;

@Service
public class TodoService {

    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);
    private final TodoRepository todoRepository;
    private final NotificationServiceClient notificationService;
    //Constructor injection
    public TodoService(TodoRepository todoRepository, NotificationServiceClient notificationService) {
        this.todoRepository = todoRepository;
        this.notificationService = notificationService;
    }

    // CREATE TODO
    public TodoResponseDTO createTodo(TodoRequestDTO dto) {
        logger.info("Creating new TODO with title: {}", dto.getTitle());
        Todo todo = new Todo();
        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());

        if (dto.getStatus() != null) {
           try {
                todo.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                logger.error("Invalid status value provided: {}", dto.getStatus());
                throw new InvalidStatusTransitionException("Invalid status value");
            }
        }
        else {
            todo.setStatus(Status.PENDING);
        }
        // create time 
        todo.setCreatedAt(LocalDateTime.now());

        Todo savedTodo = todoRepository.save(todo);
        logger.info("TODO created Successfully with ID: {}", savedTodo.getId());

        //calling notification service
        notificationService.sendNotification("New TODO created: " + savedTodo.getTitle());
        return convertToDTO(savedTodo);
    }

    // GET ALL Todos
    public List<TodoResponseDTO> getAll() {
        logger.info("Fetching all TODOs");

        return todoRepository.findAll()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // GET Todos BY ID
    public TodoResponseDTO getById(Long id) {
        logger.info("Fetching TODO with ID: {}", id);

        Todo todo = todoRepository.findById(id)
            .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        return convertToDTO(todo);
    }

    //  UPDATE
    public TodoResponseDTO update(Long id, TodoRequestDTO dto) {
        logger.info("Updating TODO with id: {}",id);

        Todo existing = todoRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("TODO not found for update with id: {}", id);
                return new TodoNotFoundException("Todo not found");
            });

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());

        if (dto.getStatus() != null) {
            Status newStatus;
            try {
                newStatus = Status.valueOf(dto.getStatus().toUpperCase());
            } 
            catch (IllegalArgumentException e) {
                logger.error("Invalid status value: {}", dto.getStatus());
                throw new InvalidStatusTransitionException("Invalid status value");
            }

           if ((existing.getStatus() == Status.PENDING && newStatus == Status.COMPLETED) ||
           (existing.getStatus() == Status.COMPLETED && newStatus == Status.PENDING)) {
            
            existing.setStatus(newStatus);
            }
            else {
               logger.error("Invalid status transition attempted");
               throw new InvalidStatusTransitionException("Invalid status transition");
            }
    }
    
        Todo updated = todoRepository.save(existing);
        logger.info("TODO updated successfully with id: {}", id);
        return convertToDTO(updated);
    }

    // DELETE
    public void delete(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("TODO not found for deletion with id: {}", id);
                    return new TodoNotFoundException("Todo not found");
                });

        todoRepository.delete(todo);
        logger.info("TODO with ID: {} deleted successfully", id);
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