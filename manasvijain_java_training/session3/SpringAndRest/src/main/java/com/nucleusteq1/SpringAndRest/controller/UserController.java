package com.nucleusteq1.SpringAndRest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nucleusteq1.SpringAndRest.model.User;
import com.nucleusteq1.SpringAndRest.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @GetMapping("/search")
    public List<User> searchUsers(  //Using @RequestParam
    @RequestParam(required = false) String name,
    @RequestParam(required = false) Integer age,
    @RequestParam(required = false) String role) {
        
        return service.searchUsers(name, age, role);
    }

}