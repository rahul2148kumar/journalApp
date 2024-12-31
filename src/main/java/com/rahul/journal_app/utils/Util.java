package com.rahul.journal_app.utils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Util {

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
}
