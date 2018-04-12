package com.example.yunjingliu.tutorial;

import org.junit.Test;

import static org.junit.Assert.*;

public class RegistrationActivityTest {
    RegistrationActivity registrationActivity = new RegistrationActivity();

    @Test
    public void InputFormatTest(){
        registrationActivity.etUsername.setText("Bili");
        assertTrue(validate(registrationActivity));
    }

    public boolean validate(RegistrationActivity registrationActivity){
        boolean valid = true;
        String username = registrationActivity.etUsername.getText().toString().trim();
        if (username.isEmpty() || username.length()>32){
            valid = false;
        }
        return valid;
    }
}

