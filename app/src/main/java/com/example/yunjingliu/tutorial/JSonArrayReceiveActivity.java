package com.example.yunjingliu.tutorial;

import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zr.auth.JsonArrayAuthRequest;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by YunjingLiu on 3/31/18.
 */

public abstract class JSonArrayReceiveActivity extends AppCompatActivity {
    public void getProfile(int method, String urls) {
        final MyApp app = (MyApp) getApplication();
        JsonArrayAuthRequest getProfileRequest = new JsonArrayAuthRequest(
                method,
                urls,
                app.getAuthProvider(),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            onReceiveSessionList(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: show error message
                //System.out.println(error.toString());
            }
        });

        RequestQueue queue = app.getRequestQueue();
        queue.add(getProfileRequest);
    }

    public abstract void onReceiveSessionList(JSONArray array) throws JSONException;
}
