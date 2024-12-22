package com.rahul.journalApp.controller;

import com.rahul.journalApp.entity.User;
import com.rahul.journalApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/public")
public class PublicController {

    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);
    @Autowired
    private UserService userService;
    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok";
    }

    @PostMapping("/create-user")
    public String createUser(@RequestBody User user){
        user.setRoles(Arrays.asList("ROLE_USER"));
        userService.saveNewUser(user);
        return "User Created Successfully";
    }
}
