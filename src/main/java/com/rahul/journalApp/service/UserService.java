package com.rahul.journalApp.service;

import com.rahul.journalApp.controller.JournalEntryControllerV2;
import com.rahul.journalApp.entity.User;
import com.rahul.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Component
@Slf4j
public class UserService {


    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    public void saveNewUser(User user){
        user.setUserName(user.getUserName().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("Register User Successfully: {}", user.getUserName());
    }

    public User saveUserEntry(User user){
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public List<User> getAllUsers(){
        List<User> allUser= userRepository.findAll();
        return allUser;
    }

    public Optional<User> findUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }
    public String deleteUserByUsername(String username){
        userRepository.deleteByUserName(username);
        return "user "+ username +" deleted";
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public Boolean addAdmin(User user) {
        try {
            user.setRoles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
            saveNewUser(user);
            log.info("User {} has been given created and have a ADMIN access");
            return true;
        }catch (Exception e){
            log.error("An error occur while adding a user : {}", e.getMessage());
        }
        return false;
    }
}
