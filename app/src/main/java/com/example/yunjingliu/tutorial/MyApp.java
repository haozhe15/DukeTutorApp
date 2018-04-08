package com.example.yunjingliu.tutorial;

import android.app.Application;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.zr.auth.AuthProvider;

/**
 * Created by YunjingLiu on 3/4/18.
 */

public class MyApp extends Application {
    private AuthProvider authProvider;
    private RequestQueue requestQueue;
    private Bundle userInfo;

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public Bundle getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Bundle userInfo) {
        this.userInfo = userInfo;
    }

    public boolean isCurrentUser(String url) {
        String userUrl = userInfo.getString("url");
        return userUrl != null && userUrl.equals(url);
    }

    public void addRequest(Request request) {
        requestQueue.add(request);
    }
}
