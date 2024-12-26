package com.rahul.journal_app.service;

import com.rahul.journal_app.entity.User;
import com.rahul.journal_app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Component
@Slf4j
public class UserService {


    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

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

    public User findUserByEmail(String email) {
        Optional<User> optionalUser=userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        return null;
    }

    public void sendForgetEmailPassword(User user) {
        final String password=generatePassword();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        String subject = "Password Recovery Request";
        String body = "Dear " + user.getUserName() + ",\n\n" +
                "We received a request to reset your password. Here is your temporary password:\n\n" +
                "User Name: " + user.getUserName() + "\n" +
                "Temporary Password: " + password + "\n\n" +
                "Please log in using this password and update it immediately to secure your account.\n" +
                "For your security, please do not share this password with anyone.\n\n" +
                "If you did not request a password reset, please contact our support team.\n\n" +
                "Best regards,\n" +
                "The Journal Application Team";
        emailService.sendMail(user.getEmail(), subject, body);
    }

    public static String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*"; // Define the allowed character set
        Random random = new Random();
        int length = random.nextInt(9) + 8; // Generate a random length between 8 and 16

        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }
}
