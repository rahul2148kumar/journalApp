package com.rahul.journalApp.service;

import com.rahul.journalApp.entity.User;
import com.rahul.journalApp.repository.UserRepository;
import lombok.Builder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testFindByUserName(){
        Assertions.assertNotNull(userRepository.findByUserName("ram"));
    }

}
