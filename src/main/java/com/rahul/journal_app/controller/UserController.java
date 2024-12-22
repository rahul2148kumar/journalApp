package com.rahul.journal_app.controller;

import com.rahul.journal_app.entity.User;
import com.rahul.journal_app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PutMapping()
    public ResponseEntity<String> updateUser(@RequestBody User user){
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
        return new ResponseEntity<>("User Updated Successfully", HttpStatus.OK);
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
