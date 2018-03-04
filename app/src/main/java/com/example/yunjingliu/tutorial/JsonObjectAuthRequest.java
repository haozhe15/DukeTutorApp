package com.example.yunjingliu.tutorial;

import android.net.sip.SipSession;
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

public class JsonObjectAuthRequest extends JsonObjectRequest {
    private BasicAuthHeader authHeader;
    public JsonObjectAuthRequest(int method, String url, String username, String password, JSONObject jsonRequest, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        authHeader = new BasicAuthHeader(username, password);
    }
    public JsonObjectAuthRequest(int method, String url, JSONObject jsonRequest, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        authHeader = new BasicAuthHeader(null, null);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return authHeader.getHeaders();
    }
}
