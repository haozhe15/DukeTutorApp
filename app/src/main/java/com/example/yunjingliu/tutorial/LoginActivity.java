package com.example.yunjingliu.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.zr.auth.AuthProvider;
import com.zr.auth.BasicAuthProvider;
import com.zr.auth.JsonArrayAuthRequest;
import com.zr.forms.JsonForm;
import com.zr.forms.JsonFormErrorListener;

import org.json.JSONArray;

public class LoginActivity extends AppCompatActivity {
    private final JsonForm form;

    public LoginActivity() {
        form = new JsonForm(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        form.put("username", R.id.etUsername);
        form.put("password", R.id.etPassword);
        final TextView etRegisterLink = (TextView) findViewById(R.id.tvRegisterHere);

        etRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRegister(v);
            }
        });
    }

    public void onClickLogin(View view) {
        final MyApp app = (MyApp) getApplication();
        final String username = form.getString("username");
        final String password = form.getString("password");
        final BasicAuthProvider authProvider = new BasicAuthProvider(username, password);
        JsonArrayAuthRequest registerRequest = new JsonArrayAuthRequest(
                Request.Method.GET,
                Backend.url("/users/"),
                authProvider,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        onLoginSuccess(authProvider);
                    }
                },
                new JsonFormErrorListener(form)
        );

        RequestQueue queue = app.getRequestQueue();
        queue.add(registerRequest);
    }

    public void onLoginSuccess(AuthProvider authProvider) {
        MyApp app = (MyApp) getApplication();
        app.setAuthProvider(authProvider);
        Intent LoginIntent = new Intent(this, UserareaActivity.class);
        startActivity(LoginIntent);
    }

    public void onClickRegister(View view) {
        //Intent registerIntent = new Intent(this, SessionAppliedActivity.class);
        Intent registerIntent = new Intent(this, RegistrationActivity.class);
        LoginActivity.this.startActivity(registerIntent);
    }
}
