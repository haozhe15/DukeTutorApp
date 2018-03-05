package com.example.yunjingliu.tutorial;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YunjingLiu on 3/4/18.
 */

public class BasicAuthHeader {
    private final String username;
    private final String password;
    public BasicAuthHeader(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("Content-Type", "application/json");
        if (username != null) {
            String encoded = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);
            params.put("Authorization", "Basic " + encoded);
        }
        return params;
    }
}
