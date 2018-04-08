package com.example.yunjingliu.tutorial;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.zr.auth.JsonObjectAuthRequest;
import com.zr.json.Conversions;

import org.json.JSONException;
import org.json.JSONObject;


public class MsgDetailActivity extends AppCompatActivity implements Response.Listener<JSONObject> {
    private final ErrorListener errorListener = new ErrorListener(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_detail);
        updateDetail(getIntent().getExtras());
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            Bundle bundle = Conversions.jsonToBundle(response);
            getIntent().putExtras(bundle);
            updateDetail(bundle);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateDetail(Bundle bundle) {
        StringBuilder b = new StringBuilder();
        final String[] fields = {"message"};
        for (String f : fields) {
            b.append(f).append(": ").append(bundle.getString(f)).append('\n');
        }
        TextView MsgDetail = findViewById(R.id.tvMsgDetail);
        MsgDetail.setText(b);
    }
}

