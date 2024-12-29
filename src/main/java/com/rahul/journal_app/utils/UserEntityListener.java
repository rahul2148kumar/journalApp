package com.rahul.journal_app.utils;

import com.rahul.journal_app.entity.User;
import org.bson.Document;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertCallback;
import org.springframework.data.mongodb.core.mapping.event.AfterLoadEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class UserEntityListener {

    @EventListener
    public void onBeforeConvert(BeforeConvertEvent<User> event){
        User user = event.getSource();
        // Convert LocalDateTime to Date and set userCreatedDate if it's null
        if (user.getUserCreatedDate() == null) {
            user.setUserCreatedDate(LocalDateTime.now());
        }
        // Always update userUpdatedDate
        user.setUserUpdatedDate(LocalDateTime.now());
    }
}
