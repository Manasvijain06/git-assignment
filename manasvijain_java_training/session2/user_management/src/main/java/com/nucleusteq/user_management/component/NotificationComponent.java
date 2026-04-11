package com.nucleusteq.user_management.component;

import org.springframework.stereotype.Component;

@Component
public class NotificationComponent {

    public String sendNotification() {
        return "Notification sent " ;
    }
}