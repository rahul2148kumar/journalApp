package com.rahul.journal_app.controller;

import com.rahul.journal_app.cache.AppCache;
import com.rahul.journal_app.entity.User;
import com.rahul.journal_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;



    @GetMapping("/clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers =userService.getAllUsers();
        if(allUsers !=null && !allUsers.isEmpty()){
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add-admin")
    public ResponseEntity<?> addAdmin(@RequestBody User user){
        Boolean isUserAdded=userService.addAdmin(user);
        if(isUserAdded!=null && isUserAdded){
            return new ResponseEntity<>(user.getUserName()+" added with Admin Access", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("An error occur to add the user", HttpStatus.NOT_ACCEPTABLE);
    }
}
