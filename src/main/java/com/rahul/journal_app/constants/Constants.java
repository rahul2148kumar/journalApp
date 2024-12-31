package com.rahul.journal_app.constants;

public interface Constants {

    String WEATHER_API_KEY="weatherApiKey";
    String CITY="city";
    String WEATHER_API_URL="weatherApiUrl";
    public static String INVALID_EMAIL_FORMAT="Invalid email format";
    public static String USER_REGISTRATION_SUCCESSFUL = "User registration successful";
    public static String INCORRECT_USERNAME_OR_PASSWORD="Incorrect username or password";
    public static String EMAIL_NOT_FOUND ="Email not found";
    public static String USER_VERIFICATION_SUCCESSFUL ="User verification successful";
    public static String USER_NOT_FOUND = "User not found";
    public static final String EMAIL_SUCCESSFULLY_SENT = "Email successfully sent";
    public static final String USER_NOT_VERIFIED = "User is not verified";





    /*-----------------------------------------Exceptions--------------------------------------------*/

    public static final String EXCEPTION_OCCURRED_DURING_USER_VERIFICATION="Exception occurred during user verification";

    public static final String PASSWORD_RESET_EXCEPTION_OCCURRED = "Exception occurred during password reset";
    public static final String INVALID_OTP_EXCEPTION = "Invalid otp exception";
    public static final String OTP_EXPIRED_EXCEPTION = "Otp expired exception";
    public static final String OTP_NULL_OR_EMPTY_EXCEPTION = "Otp null or empty exception";
    public static final String PASSWORD_RESET_SUCCESSFUL = "Password reset successful";
}
