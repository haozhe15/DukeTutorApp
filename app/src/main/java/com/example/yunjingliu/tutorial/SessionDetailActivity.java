package com.example.yunjingliu.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zr.auth.JsonObjectAuthRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by YunjingLiu on 3/27/18.
 */

public class SessionDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_detail);
        getProfile();
    }

    public void getProfile() {
        final MyApp app = (MyApp) getApplication();
        JsonObjectAuthRequest getDetailRequest = new JsonObjectAuthRequest(
                Request.Method.GET,
                getIntent().getExtras().getString("url"),
                app.getAuthProvider(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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
        queue.add(getDetailRequest);
    }

    public void onReceiveSessionList(JSONObject JObject) throws JSONException {
        TextView sessionDetail = (TextView) findViewById(R.id.tvSessionDetail);
        String title = JObject.getString("title");
        String url = JObject.getString("url");
        String description = JObject.getString("description");
        String day = JObject.getString("day");
        String time = JObject.getString("time");
        String place = JObject.getString("place");
        String msg = new String();
        msg= msg + "title: " + title + "\n" +
                "description: " + description + "\n" +
                "day: " + day + "\n" +
                "time: " + time + "\n" +
                "place: " + place + "\n" +
                "\n";
        sessionDetail.setText(msg);
    }


    public void sessionEdit(View view) {
        Intent intent = new Intent(this, TutorSessionPostActivity.class);
        Bundle msg = getIntent().getExtras();
        intent.putExtras(msg);
        startActivityForResult(intent, 1);
    }
}
