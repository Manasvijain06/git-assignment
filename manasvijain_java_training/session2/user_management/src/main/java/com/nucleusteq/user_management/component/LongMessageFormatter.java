package com.nucleusteq.user_management.component;

import org.springframework.stereotype.Component;

@Component
public class LongMessageFormatter {

    public String formatMessage() {
        return "Hello, welcome to our system. We are glad to have you here!";
    }
}