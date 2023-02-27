package com.example.springdemo.validation;

import org.springframework.stereotype.Component;

@Component
public class SqlInputValidator {

    private static final String [] BLACK_LIST = new String []{ "'", "\"", "--", "/", "\\", "&", ";","||", "and", "or", "union", "create", "delete", "drop", "update", "select"};

    public boolean isValidUsername(String username) {
        if (!isValidInput(username) ) {
            return false;
        }
        return true;
    }

    private boolean isValidInput(String input){
        for (String str: BLACK_LIST){
            if (input.contains(str)) {
                return false;
            }
        }
        return true;
    }

}
