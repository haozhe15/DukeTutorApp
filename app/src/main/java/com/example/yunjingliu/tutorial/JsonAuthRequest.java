package com.example.yunjingliu.tutorial;

import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YunjingLiu on 3/4/18.
 */

public class JsonAuthRequest extends JsonObjectRequest {
    private final String username;
    private final String password;
    public JsonAuthRequest(int method, String url, Map<String, String> map, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, new JSONObject(map), listener, errorListener);
        this.username = null;
        this.password = null;
    }
    public JsonAuthRequest(int method, String url, Map<String, String> map, String username, String password, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, new JSONObject(map), listener, errorListener);
        this.username = username;
        this.password = password;
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("Content-Type", "application/json");
        if (username != null) {
            String encoded = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);
            params.put("Authentication", "Basic " + encoded);
        }
        return params;
    }
}
