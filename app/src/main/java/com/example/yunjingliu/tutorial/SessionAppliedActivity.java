package com.example.yunjingliu.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.zr.auth.JsonArrayAuthRequest;
import com.zr.json.Conversions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by YunjingLiu on 3/31/18.
 */

public class SessionAppliedActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ApplicationListAdapter listAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_applied);
        ListView sessionList = findViewById(R.id.lvSessionApplied);
        listAdapter = new ApplicationListAdapter(this, android.R.layout.simple_list_item_1, null);
        sessionList.setAdapter(listAdapter);
        sessionList.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfile();
    }

    private void getProfile() {
        final MyApp app = (MyApp) getApplication();
        app.addRequest(new JsonArrayAuthRequest(
                Request.Method.GET,
                Backend.url("/applications/"),
                app.getAuthProvider(),
                null,
                listAdapter, new ErrorListener(this)));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            Intent intent = new Intent(this, SessionDetailActivity.class);
            JSONObject object = listAdapter.getItem(i).getJSONObject("session");
            Bundle b = Conversions.jsonToBundle(object);
            intent.putExtras(b);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
