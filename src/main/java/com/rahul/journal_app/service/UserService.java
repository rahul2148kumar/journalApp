package com.rahul.journal_app.service;

import com.rahul.journal_app.entity.JournalEntries;
import com.rahul.journal_app.entity.User;
import com.rahul.journal_app.entity.UserOtp;
import com.rahul.journal_app.model.UserDto;
import com.rahul.journal_app.model.UserOtpDto;
import com.rahul.journal_app.repository.JournalEntityRepository;
import com.rahul.journal_app.repository.UserOtpRepository;
import com.rahul.journal_app.repository.UserRepository;
import com.rahul.journal_app.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Component
@Slf4j
public class UserService {


    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserOtpRepository userOtpRepository;

    @Autowired
    private UserUtil userUtil;


    @Autowired
    private JournalEntityRepository journalEntityRepository;

    private static final SecureRandom random = new SecureRandom();




    @Transactional
    public void saveNewUser(User user){
        user.setUserName(user.getUserName().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getUserCreatedDate() == null) {
            user.setUserCreatedDate(LocalDateTime.now());
        }
        user.setUserUpdatedDate(LocalDateTime.now());
        User savedUser=userRepository.save(user);

        UserOtp userOtp = new UserOtp();
        userOtp.setUserName(user.getUserName());
        userOtp.setEmail(user.getEmail());
        userOtp.setOtp(generateOtp());
        userOtp.setOtpCreatedDateTime(LocalDateTime.now());

        UserOtp userOtpSaved = userOtpRepository.save(userOtp);
        sendOtpVerificationEmail(savedUser.getUserName(), userOtpSaved.getEmail(), userOtpSaved.getOtp());
        log.info("User Successfully Registered: {}", user.getUserName());
    }

    private void sendOtpVerificationEmail(String userName, String ToEmail, String otp) {

        String subject = "Your OTP for Account Verification";

        String body = "Dear "+userUtil.capitalizeFirstChar(userName)+",\n\n" +
                "Thank you for choosing our services! To verify your account, please use the following One-Time Password (OTP):\n\n" +
                "**Your OTP: " + otp + "**\n\n" +
                "This OTP is valid for the next 5 minutes. Please use it to complete your registration or login process.\n\n" +
                "If you did not request this OTP, please disregard this email or contact our support team immediately.\n\n" +
                "Best regards,\n" +
                "The [Your Company Name] Team\n\n" +
                "---\n" +
                "This is an automated message, please do not reply.";
        emailService.sendMail(ToEmail, subject, body);
    }

    private String generateOtp() {
        int otp = 100000+random.nextInt(900000); // generate 6-digit number
        return String.valueOf(otp);
    }

    public void updateUser(String username, User user){
        log.info("> Username: {}", username);
        User savedUserInfo=findByUserName(username);
        if(savedUserInfo !=null){
            savedUserInfo.setUserName((user.getUserName()!=null && !user.getUserName().equals(""))? user.getUserName(): savedUserInfo.getUserName());
            savedUserInfo.setPassword((user.getPassword()!=null && !user.getPassword().equals(""))? passwordEncoder.encode(user.getPassword()): savedUserInfo.getPassword());
            savedUserInfo.setCity((user.getCity()!=null && !user.getCity().equals(""))? user.getCity(): savedUserInfo.getCity());
        }
        User savedUser=userRepository.save(savedUserInfo);
        log.info("Register User Successfully: {}", user.getUserName());
    }

    public User saveUserEntry(User user){
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public List<UserDto> getAllUsers(){
        List<User> allUser= userRepository.findAll();
        List<UserDto> allUserDtoResponse= new ArrayList<>();
        for(User user: allUser){
            UserDto userDtoResponse = convertUserToUserDto(user);
            allUserDtoResponse.add(userDtoResponse);
        }
        return allUserDtoResponse;
    }

    private UserDto convertUserToUserDto(User user){
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .city(user.getCity())
                .journalEntities(user.getJournalEntities())
                .roles(user.getRoles())
                .verified(user.isVerified())
                .sentimentAnalysis(user.isSentimentAnalysis())
                .userCreatedDate(user.getUserCreatedDate())
                .userUpdatedDate(user.getUserUpdatedDate())
                .build();
        return userDto;
    }

    public Optional<User> findUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }
    @Transactional
    public String deleteUserByUsername(String username){
        try {
            User user = findByUserName(username);
            if (!user.getJournalEntities().isEmpty()) {
                deleteListOfJournal(user.getJournalEntities());
            }
            userOtpRepository.deleteByUserName(username);
            userRepository.deleteByUserName(username);
            return "user " + username + " deleted";
        }catch (Exception e){
            log.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public  void deleteListOfJournal(List<JournalEntries> journalEntriesList){
        journalEntityRepository.deleteAll(journalEntriesList);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    @Transactional
    public Boolean addAdmin(User user) {
        try {
            user.setRoles(Arrays.asList("USER", "ADMIN"));
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

    public UserDto getUserDetail(String username) {
        try {
            User user= userRepository.findByUserName(username);
            UserDto userDtoResponse=convertUserToUserDto(user);
            return userDtoResponse;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<?> verifyUser(UserOtpDto userOtpDto) {
        Optional<UserOtp> optionalUserOtp=userOtpRepository.findByEmail(userOtpDto.getEmail());
        if(optionalUserOtp.isPresent()){
            UserOtp savedUserOtp = optionalUserOtp.get();
            if(userOtpDto.getOtp()==null || userOtpDto.getOtp().equals("")){
                throw new RuntimeException("OTP can not be null or empty");
            }else if(savedUserOtp.getOtp()==null || savedUserOtp.getOtp().equals("")){
                throw new RuntimeException("Invalid OTP");
            }else if(!userOtpDto.getOtp().equals(savedUserOtp.getOtp())){
                throw new RuntimeException("Invalid OTP");
            } else if (isOtpExpired(savedUserOtp.getOtpCreatedDateTime())) {
                throw new RuntimeException("OTP Expired");
            }

            User user=findUserByEmail(userOtpDto.getEmail());
            user.setVerified(true);
            User userVerified=userRepository.save(user);

            savedUserOtp.setOtp(null);
            UserOtp userOtpUpdated=userOtpRepository.save(savedUserOtp);
            return new ResponseEntity<>("User verified", HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not Found", HttpStatus.BAD_REQUEST);
    }

    private boolean isOtpExpired(LocalDateTime otpCreatedDateTime) {
        LocalDateTime validDateTimeUntil=otpCreatedDateTime.plusMinutes(5l);
        return LocalDateTime.now().isAfter(validDateTimeUntil);
    }
}
