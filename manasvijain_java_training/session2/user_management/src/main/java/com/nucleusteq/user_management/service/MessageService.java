package com.nucleusteq.user_management.service;

import org.springframework.stereotype.Service;

import com.nucleusteq.user_management.component.LongMessageFormatter;
import com.nucleusteq.user_management.component.ShortMessageFormatter;

@Service
public class MessageService {

    private final ShortMessageFormatter shortFormatter;
    private final LongMessageFormatter longFormatter;

    public MessageService(ShortMessageFormatter shortFormatter, LongMessageFormatter longFormatter) {
        this.shortFormatter = shortFormatter;
        this.longFormatter = longFormatter;
    }

    public String getMessage(String type) {

        //FIX (Null safe)
        if ("SHORT".equalsIgnoreCase(type)) {
            return shortFormatter.formatMessage();
        }

        return longFormatter.formatMessage();
    }
}