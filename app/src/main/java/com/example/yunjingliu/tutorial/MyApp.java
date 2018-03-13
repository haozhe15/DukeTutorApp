package com.example.yunjingliu.tutorial;

import android.app.Application;
import android.os.Bundle;

/**
 * Created by YunjingLiu on 3/4/18.
 */

public class MyApp extends Application{
    private AuthProvider authProvider;

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }
}
