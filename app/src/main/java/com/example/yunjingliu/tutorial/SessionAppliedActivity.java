package com.example.yunjingliu.tutorial;

import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.zr.auth.JsonArrayAuthRequest;
import com.zr.json.Conversions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by YunjingLiu on 3/31/18.
 */

public class SessionAppliedActivity extends AppCompatActivity implements Response.Listener<JSONArray>, AdapterView.OnItemClickListener{
   // ArrayList<Bundle> urls = new ArrayList<>();
   // ArrayList<String> status = new ArrayList<>();
    ApplicationListAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_applied);
        getProfile();
        ListView sessionList = (ListView) findViewById(R.id.lvSessionApplied);
        adapter = new ApplicationListAdapter(this, android.R.layout.simple_list_item_1, null);
        sessionList.setAdapter(adapter);
        sessionList.setOnItemClickListener(this);

    }

    private void getProfile() {
        final MyApp app = (MyApp) getApplication();
        JsonArrayAuthRequest getProfileRequest = new JsonArrayAuthRequest(
                Request.Method.GET,
                Backend.url("/applications/"),
                app.getAuthProvider(),
                null,
                this, new ErrorListener(this));
        RequestQueue queue = app.getRequestQueue();
        queue.add(getProfileRequest);
    }

    public void onResponse(JSONArray array) {
        adapter.setJsonArray(array);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            Intent intent = new Intent(this, SessionDetailActivity.class);
            JSONObject object = adapter.getItem(i);
            Bundle b = Conversions.jsonToBundle(object);
            b.putString("apply", "no");
            intent.putExtras(b);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
