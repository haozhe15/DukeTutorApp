package com.example.yunjingliu.tutorial;

import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rui on 3/13/18.
 */

public class JsonFormErrorListener implements Response.ErrorListener {
    private final JsonForm form;

    public JsonFormErrorListener(JsonForm form) {
        this.form = form;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // TODO deal with network failure, etc.
        NetworkResponse response = error.networkResponse;
        if (response != null && response.data != null) {
            try {
                String jsonString = new String(response.data);
                JSONObject errorObject = new JSONObject(jsonString);
                String detail = errorObject.optString("detail");
                if (!detail.isEmpty()) {
                    // TODO show error message.
                }
                form.setError(errorObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
