package com.nucleusteq.user_management.service;

import org.springframework.stereotype.Service;

import com.nucleusteq.user_management.component.NotificationComponent;

@Service
public class NotificationService {

    private final NotificationComponent component;

    public NotificationService(NotificationComponent component) {
        this.component = component;
    }

    public String triggerNotification() {
        return component.sendNotification();
    }
}