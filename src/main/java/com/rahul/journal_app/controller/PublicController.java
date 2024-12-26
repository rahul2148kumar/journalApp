package com.rahul.journal_app.controller;

import com.rahul.journal_app.api.response.TwitterUser;
import com.rahul.journal_app.entity.User;
import com.rahul.journal_app.service.TwitterService;
import com.rahul.journal_app.service.UserDetailsServiceImpl;
import com.rahul.journal_app.service.UserService;
import com.rahul.journal_app.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {



    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

    private final UserService userService;
    private final TwitterService twitterService;

    public PublicController(UserService userService, TwitterService twitterService) {
        this.userService = userService;
        this.twitterService = twitterService;
    }


    @GetMapping("/health-check")
    public String healthCheck(){
        log.info("> Trigger health-check API");
        return "ok";
    }

    @GetMapping("/user-tweets")
    public ResponseEntity<?> getUserTwitterTweets(@RequestParam("id") String id){
        TwitterUser twitterUser = twitterService.getTweet(id);
        if(twitterUser!=null){
            return new ResponseEntity<>(twitterUser.getDisplayText(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // create user
    @PostMapping("/signup")
    public String signup(@RequestBody User user){
        user.setRoles(Arrays.asList("USER"));
        userService.saveNewUser(user);
        return "User Created Successfully";
    }

    // create jwt token
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        log.info("Start login");
        if(user.getUserName() !=null && !user.getUserName().equals("")){
            user.setUserName(user.getUserName().toLowerCase());
        }

        try {
            // try to check user authentication
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );
            log.info("User authenticated: {}", user.getUserName());


            UserDetails userDetails =userDetailsServiceImpl.loadUserByUsername(user.getUserName());
            String jwt=jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception occurred while creatingAuthenticationToken: {}", e.getMessage());
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/send-forget-password-email")
    public ResponseEntity<String> getForgetPasswordEmail(@RequestParam("email") String email){

        User user=userService.findUserByEmail(email);
        if(user !=null){
            userService.sendForgetEmailPassword(user);
            return new ResponseEntity<>("A email is sent to the user with temporary password", HttpStatus.OK);
        }
        return new ResponseEntity<>("Email not found", HttpStatus.BAD_REQUEST);
    }

}
