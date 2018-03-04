package com.example.yunjingliu.tutorial;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YunjingLiu on 3/4/18.
 */

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://vcm-3307.vm.duke.edu:8000/users/";
    private Map<String,String> params;
    public RegisterRequest(String username, String firstname, String lastname, String email, String password, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener,null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("email", email);
        params.put("first_name", firstname);
        params.put("last_name", lastname);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
