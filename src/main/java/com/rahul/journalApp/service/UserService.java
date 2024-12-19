package com.rahul.journalApp.service;

import com.rahul.journalApp.entity.User;
import com.rahul.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@Component
public class UserService {



    @Autowired
    private UserRepository userRepository;

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

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
