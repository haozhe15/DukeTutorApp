package com.example.yunjingliu.tutorial;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.yunjingliu.tutorial.helper_class.ErrorListener;
import com.example.yunjingliu.tutorial.helper_class.MyApp;
import com.zr.auth.JsonObjectAuthRequest;
import com.zr.json.Conversions;

import org.json.JSONException;
import org.json.JSONObject;


public class MsgDetailActivity extends AppCompatActivity implements Response.Listener<JSONObject> {
    private final ErrorListener errorListener = new ErrorListener(this);
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_detail);
        updateDetail(getIntent().getExtras());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        return true;
    }

    private void onAcceptClick(boolean is_open) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (is_open) {
            builder.setMessage("Accept this application?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            doAccept();
                        }
                    })
                    .setNegativeButton("No", null);
            builder.show();
        } else {
            builder.setMessage("You have already accepted others for this session.")
                    .setNegativeButton("Back", null);
            builder.show();
        }

    }

    private void doAccept() {
        try {
            final MyApp app = (MyApp) getApplication();
            JSONObject requestBody = new JSONObject();
            requestBody.put("accepted", true);
            String url = getIntent().getStringExtra("application");

            app.addRequest(new JsonObjectAuthRequest(
                    Request.Method.PATCH,
                    url,
                    app.getAuthProvider(),
                    requestBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            onAcceptResponse(response);
                        }
                    }, errorListener));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void onAcceptResponse(JSONObject response) {
        Toast toast = Toast.makeText(this, "Application accepted", Toast.LENGTH_SHORT);
        toast.show();
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
        final String[] fields = {"message", "timestamp"};
        for (String f : fields) {
            b.append(f).append(": ").append(bundle.getString(f)).append('\n');
        }
        TextView MsgDetail = findViewById(R.id.tvMsgDetail);
        MsgDetail.setText(b);
        MyApp app = (MyApp) getApplication();
        app.addRequest(new JsonObjectAuthRequest(
                Request.Method.GET,
                bundle.getString("application"),
                app.getAuthProvider(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        onReceiveApplication(response);
                    }
                },
                errorListener));

        app.addRequest(new JsonObjectAuthRequest(
                Request.Method.GET,
                bundle.getString("sender"),
                app.getAuthProvider(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        onReceiveUser(response);
                    }
                },
                errorListener));

    }

    private void onReceiveUser(JSONObject User) {
        try {
            String username = User.getString("username");
            StringBuilder sb = new StringBuilder();
            sb.append("send from: ").append(username);
            TextView MsgDetail = findViewById(R.id.tvUsername);
            MsgDetail.setText(sb);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void onReceiveApplication(JSONObject application) {
        try {
            String url = application.getString("session");
            String status;
            final boolean isUndecided = application.isNull("accepted");
            status = application.getString("accepted");
            if (status.equals("null")) {
                status = "Undecided";
            } else if (status.equals("true")) {
                status = "Accepted";
            } else {
                status = "Declined";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("application status: ").append(status);
            TextView MsgDetail = findViewById(R.id.tvAppStatus);
            MsgDetail.setText(sb);
            MyApp app = (MyApp) getApplication();
            app.addRequest(new JsonObjectAuthRequest(
                    Request.Method.GET,
                    url,
                    app.getAuthProvider(),
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            onReceiveSession(response, isUndecided);
                        }
                    }
            , errorListener));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void onReceiveSession(JSONObject session, boolean isUndecided) {
        try {
            String tutor = session.getString("tutor");
            String title = session.getString("title");
            final boolean is_open = session.getBoolean("is_open");
            MyApp app = (MyApp) getApplication();
            if (app.isCurrentUser(tutor) && isUndecided) { //request user is tutor, application is undecided
                MenuItem edit = menu.add("Accept");
                edit.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        onAcceptClick(is_open);
                        return true;
                    }
                });
            };
            StringBuilder sb = new StringBuilder();
            sb.append("session title: ").append(title);
            TextView MsgDetail = findViewById(R.id.tvSessionTitle);
            MsgDetail.setText(sb);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

