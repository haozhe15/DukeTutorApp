package com.example.yunjingliu.tutorial;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by rui on 4/1/18.
 */

class ErrorListener implements Response.ErrorListener {
    private final Context context;

    public ErrorListener(Context context) {
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String msg = error.getMessage();
        if (msg == null) {
            msg = new String(error.networkResponse.data);
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.show();
    }
}