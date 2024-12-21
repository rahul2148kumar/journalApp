package com.rahul.journalApp.controller;

import com.rahul.journalApp.entity.User;
import com.rahul.journalApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;


    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user){
        logger.info("> User Update begin...");
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        logger.info("> Username: {}", username);
        User userInfo=userService.findByUserName(username);
        if(userInfo !=null){
            userInfo.setUserName(user.getUserName());
            userInfo.setPassword(user.getPassword());
            userService.saveNewUser(userInfo);
        }
        logger.info("> Updated user Successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUser(){
        logger.info("> Deleting user...");
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        logger.info("> Username: {}", username);
        String result=userService.deleteUserByUsername(username);
        logger.info("> Deleted User");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



}
