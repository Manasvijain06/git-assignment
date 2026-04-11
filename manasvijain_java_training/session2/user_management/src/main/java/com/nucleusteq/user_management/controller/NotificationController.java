package com.nucleusteq.user_management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nucleusteq.user_management.service.NotificationService;

@RestController
@RequestMapping("/notify")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping("/{name}")
    public String sendNotification(@PathVariable String name) {
        return service.triggerNotification(name);
    }
}