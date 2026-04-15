package com.Nucleusteq.SpringAndRestAssignment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Nucleusteq.SpringAndRestAssignment.exception.InvalidUserException;
import com.Nucleusteq.SpringAndRestAssignment.model.User;
import com.Nucleusteq.SpringAndRestAssignment.service.UserService;

@RestController
// SEARCH 
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @GetMapping("/search")
    public List<User> searchUsers(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Integer age,
        @RequestParam(required = false) String role) {
        
    return service.searchUsers(name, age, role);
    }

    // SUBMIT
    @PostMapping("/submit")
    public ResponseEntity<String> submitUser(@RequestBody(required = false) User user) {
       
        if (user == null) {
            throw new InvalidUserException("Invalid input: Request body is missing");
        } 

        User SavedUser = service.submitUser(user);

        return new ResponseEntity<>("201 --> Submission successful", HttpStatus.CREATED);
    }
        
    // DELETE
    
    @DeleteMapping("/{id}")
    public String deleteUser(
        @PathVariable int id,
        @RequestParam(required = false) Boolean confirm) {

    return service.deleteUser(id, confirm);
}
}

