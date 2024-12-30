package com.rahul.journal_app.utils;

import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    public String capitalizeFirstChar(String input) {
        if (input == null || input.isEmpty()) {
            return input; // Return as is for null or empty input
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
