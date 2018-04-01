package com.zr.auth;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by YunjingLiu on 3/4/18.
 */

public class JsonObjectAuthRequest extends JsonAuthRequest<JSONObject> {
    /**
     * Constructor.
     *
     * @param method        The request method.
     * @param url           The URL of the request.
     * @param auth          The AuthProvider object (null for no authorization).
     * @param requestBody   The request body (null for no request body).
     * @param listener      The response listener.
     * @param errorListener The error listener.
     */
    public JsonObjectAuthRequest(int method, String url, AuthProvider auth,
                                 Object requestBody,
                                 Response.Listener<JSONObject> listener,
                                 Response.ErrorListener errorListener) {
        super(method, url, auth, requestBody, listener, errorListener);
    }

    @Override
    protected JSONObject parseJson(String jsonString) throws JSONException {
        return new JSONObject(jsonString);
    }
}
