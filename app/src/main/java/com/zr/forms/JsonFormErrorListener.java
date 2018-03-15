package com.zr.forms;

import android.widget.Toast;

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
        NetworkResponse response = error.networkResponse;
        if (response != null && response.data != null) {
            try {
                String jsonString = new String(response.data);
                JSONObject errorObject = new JSONObject(jsonString);
                String detail = errorObject.optString("detail");
                if (!detail.isEmpty()) {
                    generalError(detail);
                }
                form.setError(errorObject);
            } catch (JSONException e) {
                e.printStackTrace();
                generalError(error.getMessage());
            }
        } else {
            generalError(error.getMessage());
        }
    }

    protected void generalError(String error) {
        Toast toast = Toast.makeText(form.getActivity(), error, Toast.LENGTH_LONG);
        toast.show();
    }
}
