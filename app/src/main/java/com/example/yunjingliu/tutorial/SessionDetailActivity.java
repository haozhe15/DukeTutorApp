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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.zr.auth.JsonObjectAuthRequest;
import com.zr.json.Conversions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by YunjingLiu on 3/27/18.
 */

public class SessionDetailActivity extends AppCompatActivity implements Response.Listener<JSONObject> {
    private final ErrorListener errorListener = new ErrorListener(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_detail_show);
        updateDetail();
        requestDetail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MyApp app = (MyApp) getApplication();
        Intent intent = getIntent();
        String tutor = intent.getStringExtra("tutor");
        if (app.isCurrentUser(tutor)) {
            MenuItem edit = menu.add("Edit");
            edit.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    onEditClick();
                    return true;
                }
            });
        } else if (intent.getBooleanExtra("can_apply", false)) {
            MenuItem apply = menu.add("Apply");
            apply.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            apply.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    onApplyClick();
                    return true;
                }
            });
        }
        return true;
    }

    public void requestDetail() {
        final MyApp app = (MyApp) getApplication();
        JsonObjectAuthRequest getDetailRequest = new JsonObjectAuthRequest(
                Request.Method.GET,
                getIntent().getStringExtra("url"),
                app.getAuthProvider(),
                null,
                this, errorListener);

        RequestQueue queue = app.getRequestQueue();
        queue.add(getDetailRequest);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        requestDetail();
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

    private void onEditClick() {
        Intent intent = new Intent(this, TutorSessionPostActivity.class);
        intent.putExtras(getIntent());
        startActivityForResult(intent, 1);
    }

    private void onApplyClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apply to this session?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        doApply();
                    }
                })
                .setNegativeButton("No", null);
        AlertDialog dialog = builder.show();
    }

    private void doApply() {
        try {
            final MyApp app = (MyApp) getApplication();
            JSONObject requestBody = new JSONObject();
            requestBody.put("session", getIntent().getStringExtra("url"));
            JsonObjectAuthRequest request = new JsonObjectAuthRequest(
                    Request.Method.POST,
                    Backend.url("/applications/"),
                    app.getAuthProvider(),
                    requestBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            onApplyResponse(response);
                        }
                    }, errorListener);
            app.getRequestQueue().add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void onApplyResponse(JSONObject response) {
        Toast toast = Toast.makeText(this, "Application sent", Toast.LENGTH_SHORT);
        toast.show();
    }
}