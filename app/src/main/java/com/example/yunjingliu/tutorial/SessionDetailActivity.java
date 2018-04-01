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
import com.zr.json.Conversions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by YunjingLiu on 3/27/18.
 */

public class SessionDetailActivity extends AppCompatActivity implements Response.Listener<JSONObject> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_detail_show);
        updateDetail();
    }

    public void makeRequest() {
        final MyApp app = (MyApp) getApplication();
        JsonObjectAuthRequest getDetailRequest = new JsonObjectAuthRequest(
                Request.Method.GET,
                getIntent().getStringExtra("url"),
                app.getAuthProvider(),
                null,
                this, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: show error message
                //System.out.println(error.toString());
            }
        });

        RequestQueue queue = app.getRequestQueue();
        queue.add(getDetailRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        makeRequest();
    }

    private void updateDetail() {
        // TODO nicer appearance?
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        StringBuilder b = new StringBuilder();
        final String[] fields = {"title", "description", "day", "time", "place"};
        for (String f : fields) {
            b.append(f).append(": ").append(bundle.getString(f)).append('\n');
        }
        TextView sessionDetail = findViewById(R.id.tvSessionDetail);
        sessionDetail.setText(b);
    }

    public void sessionBack(View view) {
        finish();
    }

    public void sessionEdit(View view) {
        Intent intent = new Intent(this, TutorSessionPostActivity.class);
        intent.putExtras(getIntent());
        startActivityForResult(intent, 1);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            Bundle bundle = Conversions.jsonToBundle(response);
            getIntent().putExtras(bundle);
            updateDetail();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}