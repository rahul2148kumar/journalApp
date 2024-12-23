package com.rahul.journal_app.controller;

import com.rahul.journal_app.api.response.WeatherResponse;
import com.rahul.journal_app.entity.User;
import com.rahul.journal_app.service.UserService;
import com.rahul.journal_app.service.WeatherService;
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

    private final WeatherService weatherService;

    public UserController(UserService userService, WeatherService weatherService) {
        this.userService = userService;
        this.weatherService = weatherService;
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
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        String result=userService.deleteUserByUsername(username);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<?> greeting(@RequestParam("citi") String citi){
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        String feelsLikeTemp="NA";
        WeatherResponse weatherResponse=weatherService.getWeather(citi);
        if(weatherResponse!=null){
            if(weatherResponse.getCurrent()!=null){
                feelsLikeTemp= String.valueOf(weatherResponse.getCurrent().getFeelslike());
            }
        }
        return new ResponseEntity<>("Hi "+username+", Weather feels like "+feelsLikeTemp, HttpStatus.OK);
    }

}
