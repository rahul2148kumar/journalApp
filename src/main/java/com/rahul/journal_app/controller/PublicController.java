package com.rahul.journal_app.controller;

import com.rahul.journal_app.api.response.TwitterUser;
import com.rahul.journal_app.entity.User;
import com.rahul.journal_app.service.TwitterService;
import com.rahul.journal_app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {



    private final UserService userService;
    private final TwitterService twitterService;

    public PublicController(UserService userService, TwitterService twitterService) {
        this.userService = userService;
        this.twitterService = twitterService;
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

    @GetMapping("/user-tweets")
    public ResponseEntity<?> getUserTwitterTweets(@RequestParam("id") String id){
        TwitterUser twitterUser = twitterService.getTweet(id);
        if(twitterUser!=null){
            return new ResponseEntity<>(twitterUser.getDisplayText().toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
