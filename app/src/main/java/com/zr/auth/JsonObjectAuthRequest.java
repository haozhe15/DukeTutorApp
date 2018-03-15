package com.zr.auth;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YunjingLiu on 3/4/18.
 */

/**
 * A JsonObjectRequest that contains authorization information.
 */
public class JsonObjectAuthRequest extends JsonObjectRequest {
    private JsonRequestAuthAdapter authAdapter;

    /**
     * Constructor.
     * @param method The request method.
     * @param url The URL of the request.
     * @param auth The AuthProvider object (null for no authorization).
     * @param jsonRequest The JSON request object (null for no request body).
     * @param listener The response listener.
     * @param errorListener The error listener.
     */
    public JsonObjectAuthRequest(int method, String url, AuthProvider auth, JSONObject jsonRequest,
                                 Response.Listener<JSONObject> listener,
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
