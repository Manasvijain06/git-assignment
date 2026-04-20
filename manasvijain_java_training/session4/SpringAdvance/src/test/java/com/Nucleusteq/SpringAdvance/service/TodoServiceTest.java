package com.Nucleusteq.SpringAdvance.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.Nucleusteq.SpringAdvance.dto.TodoRequestDTO;
import com.Nucleusteq.SpringAdvance.dto.TodoResponseDTO;
import com.Nucleusteq.SpringAdvance.entity.Todo;
import com.Nucleusteq.SpringAdvance.entity.Todo.Status;
import com.Nucleusteq.SpringAdvance.exception.InvalidStatusTransitionException;
import com.Nucleusteq.SpringAdvance.exception.TodoNotFoundException;
import com.Nucleusteq.SpringAdvance.repository.TodoRepository;

public class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;

    @Mock
    private NotificationServiceClient notificationService;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // ================= HELPER METHODS =================
    private Todo createTodo(Long id, String title, Status status) {
        Todo t = new Todo();
        t.setId(id);
        t.setTitle(title);
        t.setStatus(status);
        t.setCreatedAt(LocalDateTime.now());
        return t;
    }

    private TodoRequestDTO createRequest(String title, String status) {
        TodoRequestDTO dto = new TodoRequestDTO();
        dto.setTitle(title);
        dto.setStatus(status);
        return dto;
    }

   // ================= CREATE =================
    @Test
    void testCreateTodo() {
        TodoRequestDTO dto = createRequest("Task 1", "PENDING");
        
        when(todoRepository.save(any(Todo.class)))
        .thenReturn(createTodo(1L, "Task 1", Status.PENDING));

        TodoResponseDTO result = todoService.createTodo(dto);

        assertNotNull(result);
        assertEquals("Task 1", result.getTitle());
        assertEquals("PENDING", result.getStatus());

        verify(notificationService, times(1))
                .sendNotification(anyString());
    }
    @Test
    void testCreateTodo_defaultStatus() {

        TodoRequestDTO dto = createRequest("Task 2", null);

        when(todoRepository.save(any(Todo.class)))
                .thenReturn(createTodo(2L, "Task 2", Status.PENDING));

        TodoResponseDTO response = todoService.createTodo(dto);

        assertEquals("PENDING", response.getStatus());
    }

    @Test
    void testCreateTodo_invalidStatus() {

        TodoRequestDTO dto = createRequest("Task", "WRONG");

        assertThrows(InvalidStatusTransitionException.class,
                () -> todoService.createTodo(dto));
    }
    
    // ================= GET ALL =================

    @Test
     void testGetAll() {

        List<Todo> todos = List.of(
                createTodo(1L, "Task 1", Status.PENDING),
                createTodo(2L, "Task 2", Status.COMPLETED)
        );

        when(todoRepository.findAll()).thenReturn(todos);

        List<TodoResponseDTO> result = todoService.getAll();

        assertEquals(2, result.size());
    }
    
    // ================= GET BY ID =================

     @Test
    void testGetById_success() {

        when(todoRepository.findById(1L))
                .thenReturn(Optional.of(createTodo(1L, "Task", Status.PENDING)));

        TodoResponseDTO response = todoService.getById(1L);

        assertEquals("Task", response.getTitle());
    }

    @Test
    void testGetById_notFound() {

        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class,
                () -> todoService.getById(1L));
    }
    // ================= UPDATE =================

    @Test
    void testUpdate() {

        Todo existing = createTodo(1L, "Old", Status.PENDING);

        TodoRequestDTO dto = createRequest("Updated", "COMPLETED");

        when(todoRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(todoRepository.save(any(Todo.class))).thenReturn(existing);

        TodoResponseDTO response = todoService.update(1L, dto);

        assertEquals("Updated", response.getTitle());
        assertEquals("COMPLETED", response.getStatus());
    }

      @Test
    void testUpdate_notFound() {

        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class,
                () -> todoService.update(1L, new TodoRequestDTO()));
    }

    // ================= DELETE =================
    
    @Test
    void testDelete() {

        Todo todo = createTodo(1l,"Task", Status.PENDING);
    
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        todoService.delete(1L);

        verify(todoRepository, times(1)).delete(todo);
    }

    @Test
    void testDelete_notFound() {

        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class,() ->
            todoService.delete(1L));
    }

}