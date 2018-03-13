package com.example.yunjingliu.tutorial;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;

import java.util.Map;

/**
 * Created by rui on 3/13/18.
 */

class JsonRequestAuthAdapter {
    private AuthProvider auth;
    public JsonRequestAuthAdapter(AuthProvider auth) {
        this.auth = auth;
    }

    public void updateHeaders(Map<String, String> headers) {
        if (auth == null) {
            return; // no authorization
        }
        String authorization = auth.getAuthorization();
        if (authorization != null) {
            headers.put("Authorization", authorization);
        }
    }
}
