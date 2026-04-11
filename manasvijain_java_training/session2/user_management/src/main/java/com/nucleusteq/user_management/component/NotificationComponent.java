package com.nucleusteq.user_management.component;

import org.springframework.stereotype.Component;

@Component
public class NotificationComponent {

    public String sendNotification(String name) {
        return "Notification sent to " + name;
    }
}