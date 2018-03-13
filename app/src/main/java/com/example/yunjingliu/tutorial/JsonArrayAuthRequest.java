package com.example.yunjingliu.tutorial;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YunjingLiu on 3/4/18.
 */

class JsonArrayAuthRequest extends JsonArrayRequest {
    private JsonRequestAuthAdapter authAdapter;
    public JsonArrayAuthRequest(int method, String url, AuthProvider auth, JSONArray jsonRequest,
                                Response.Listener<JSONArray> listener,
                                Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        authAdapter = new JsonRequestAuthAdapter(auth);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<>();
        authAdapter.updateHeaders(headers);
        return headers;
    }
}
