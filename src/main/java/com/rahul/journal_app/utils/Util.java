package com.rahul.journal_app.utils;

import com.rahul.journal_app.entity.User;
import com.rahul.journal_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
public class Util {

    @Autowired
    private UserRepository userRepository;

    public String capitalizeFirstChar(String input) {
        if (input == null || input.isEmpty()) {
            return input; // Return as is for null or empty input
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    @Transactional
    public boolean isValidEmail(String email){
        if(email!=null && email.contains("@")){
            int atIndex=email.indexOf("@");
            int dotIndex=email.indexOf(".", atIndex);
            return atIndex>0 && dotIndex>atIndex+1 && dotIndex<email.length()-1;
        }
        return false;
    }

    public String getBodyForResetPasswordSendOtpMail(String userName, String otp) {
        String body = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; }" +
                "p { margin: 10px 0; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<p>Dear " +userName + ",</p>" +
                "<p>We received a request to reset your password. Here is your One-Time Password (OTP):</p>" +
                "<p><b>OTP: " + otp + "</b></p>" +
                "<p>Please use this OTP to reset your password. It is valid for 5 minutes. " +
                "For your security, please do not share this OTP with anyone. " +
                "If you did not request a password reset, please contact our support team immediately.</p>" +
                "<p>Best regards,<br>The Journal Application Team</p>" +
                "</body>" +
                "</html>";
        return body;
    }

    public boolean isValidPhoneNumber(String phoneNo) {
        if(phoneNo.length()!=10) return false;
        return phoneNo.chars().allMatch(Character::isDigit); // Ensure all characters are digits
    }

    public boolean isValidDateOfBirth(String dateOfBirth, String format) {
        if (dateOfBirth == null || format == null) {
            return false; // Invalid if either date or format is null
        }
        DateTimeFormatter formatter;
        try {
            formatter = DateTimeFormatter.ofPattern(format);
        } catch (IllegalArgumentException e) {
            return false; // Invalid format pattern
        }

        try {

            LocalDate.parse(dateOfBirth, formatter);
            return true; // Date is valid
        } catch (DateTimeParseException e) {
            return false; // Date is invalid
        }
    }

    public boolean isAdmin(String userName){
        User user = userRepository.findByUserName(userName);
        boolean hasUserAdminRole = false;
        if(user!=null){
            List<String> roles = user.getRoles();
            for(String role: roles){
                if(role.toUpperCase().equals("ADMIN")){
                    hasUserAdminRole=true;
                    break;
                }
            }

        }
        return hasUserAdminRole;
    }
}
