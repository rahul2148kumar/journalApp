package com.rahul.journalApp.controller;

import com.rahul.journalApp.entity.User;
import com.rahul.journalApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryControllerV2.class);
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }


    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.saveUserEntry(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable("username") String username, @RequestBody User user){
        logger.info("> user update start...");
        User userInfo=userService.findByUserName(username);
        if(userInfo !=null){
            userInfo.setUserName(user.getUserName());
            userInfo.setPassword(user.getPassword());
            userService.saveUserEntry(userInfo);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
