package com.example.yunjingliu.tutorial;

import android.util.Base64;

/**
 * Created by YunjingLiu on 3/4/18.
 */

public class BasicAuthProvider implements AuthProvider {
    private String authorization;

    public void setUsernamePassword(String username, String password) {
        String userpass = username + ":" + password;
        authorization = "Basic " + Base64.encodeToString(userpass.getBytes(), Base64.NO_WRAP);
    }

    @Override
    public String getAuthorization() {
        return authorization;
    }
}
