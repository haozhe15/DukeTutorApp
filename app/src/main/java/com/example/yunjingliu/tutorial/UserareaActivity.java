package com.example.yunjingliu.tutorial;

import android.content.Intent;
import android.os.Bundle;
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
 * Created by Haozhe Wang on 3/4/18.
 */

public class UserareaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private SessionListAdapter sessionListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userarea);
        // TODO welcome message is removed

        // we can use app.getUserInfo to get user name, etc.

        ListView sessionList = findViewById(R.id.lvSessionList);
        sessionListAdapter = new SessionListAdapter(this, android.R.layout.simple_list_item_1, null);
        sessionList.setAdapter(sessionListAdapter);
        sessionList.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfile();
    }

    public void addNew(View view) {
        Intent intent = new Intent(this, TutorSessionPostActivity.class);
        startActivity(intent);
    }

    public void skipToSearch(View view) {
        Intent intent = new Intent(this, SearchableActivity.class);
        startActivity(intent);
    }

    public void checkSessionApplied(View view) {
        /*Intent intent = new Intent(this, SessionAppliedActivity.class);
        startActivity(intent);*/
    }

    public void checkMsg(View view) {
        Intent intent = new Intent(this, MsgActivity.class);
        startActivity(intent);
    }

    private void getProfile() {
        final MyApp app = (MyApp) getApplication();
        app.addRequest(new JsonArrayAuthRequest(
                Request.Method.GET,
                Backend.url("/sessions/"),
                app.getAuthProvider(),
                null,
                sessionListAdapter, new ErrorListener(this)));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            Intent intent = new Intent(this, SessionDetailActivity.class);
            JSONObject object = sessionListAdapter.getItem(i);
            Bundle b = Conversions.jsonToBundle(object);
            intent.putExtras(b);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

