package com.example.yunjingliu.tutorial;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by YunjingLiu on 3/4/18.
 */

public class JsonArrayAuthRequest extends JsonArrayRequest{
    private BasicAuthHeader authHeader;
    public JsonArrayAuthRequest(int method, String url, String username, String password, JSONArray jsonRequest, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        authHeader = new BasicAuthHeader(username, password);
    }
    public JsonArrayAuthRequest(int method, String url, JSONArray jsonRequest, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        authHeader = new BasicAuthHeader(null, null);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return authHeader.getHeaders();
    }
}
