package com.example.yunjingliu.tutorial;

import android.content.Intent;
import android.media.MediaCas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.zr.auth.JsonArrayAuthRequest;
import com.zr.json.Conversions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Haozhe Wang on 3/4/18.
 */

public class UserareaActivity extends AppCompatActivity implements Response.Listener<JSONArray>, AdapterView.OnItemClickListener {
    private SessionListAdapter sessionListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userarea);
        // TODO welcome message is removed

        // we can use app.getUserInfo to get user name, etc.

        getProfile();
        ListView sessionList = findViewById(R.id.lvSessionList);
        sessionListAdapter = new SessionListAdapter(this, android.R.layout.simple_list_item_1, null);
        sessionList.setAdapter(sessionListAdapter);
        sessionList.setOnItemClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getProfile();
    }

    public void addNew(View view) {
        Intent intent = new Intent(this, TutorSessionPostActivity.class);
        //startActivity(intent);
        startActivityForResult(intent, 1);
    }

    public void skipToSearch(View view) {
        Intent intent = new Intent(this, SearchableActivity.class);
       // Intent intent = new Intent(this, SessionAppliedActivity.class);
        startActivity(intent);
    }
    public void checkSessionApplied(View view) {
        //Intent intent = new Intent(this, SearchableActivity.class);
        Intent intent = new Intent(this, SessionAppliedActivity.class);
        startActivity(intent);
    }
    public void checkMsg(View view) {
        //Intent intent = new Intent(this, SearchableActivity.class);
        Intent intent = new Intent(this, MsgActivity.class);
        startActivity(intent);
    }


    private void getProfile() {
        final MyApp app = (MyApp) getApplication();
        JsonArrayAuthRequest getProfileRequest = new JsonArrayAuthRequest(
                Request.Method.GET,
                Backend.url("/sessions/"),
                app.getAuthProvider(),
                null,
                this, new ErrorListener(this));
        RequestQueue queue = app.getRequestQueue();
        queue.add(getProfileRequest);
    }

    public void onResponse(JSONArray array) {
        sessionListAdapter.setJsonArray(array);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            Intent intent = new Intent(this, SessionDetailActivity.class);
            JSONObject object = sessionListAdapter.getItem(i);
            Bundle b = Conversions.jsonToBundle(object);
            b.putString("apply", "yes");
            intent.putExtras(b);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

