package com.rahul.journal_app.controller;

import com.rahul.journal_app.entity.User;
import com.rahul.journal_app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {



    private final UserService userService;

    public PublicController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/health-check")
    public String healthCheck(){
        log.debug("> Trigger health-check API");
        return "ok";
    }

    @PostMapping("/create-user")
    public String createUser(@RequestBody User user){
        user.setRoles(Arrays.asList("ROLE_USER"));
        userService.saveNewUser(user);
        return "User Created Successfully";
    }
}
