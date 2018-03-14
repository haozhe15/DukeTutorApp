package com.example.yunjingliu.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zr.auth.AuthProvider;
import com.zr.auth.BasicAuthProvider;
import com.zr.auth.JsonArrayAuthRequest;

import org.json.JSONArray;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername;
    EditText etPassword;
    TextView etRegisterLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRegisterLink = (TextView) findViewById(R.id.tvRegisterHere);

        etRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRegister(v);
            }
        });
    }

    public void onClickLogin(View view) {
        final MyApp app = (MyApp) getApplication();
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();
        final BasicAuthProvider authProvider = new BasicAuthProvider(username, password);
        JsonArrayAuthRequest registerRequest = new JsonArrayAuthRequest(
                Request.Method.GET, "http://vcm-3307.vm.duke.edu:8000/users/",
                authProvider,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        onLoginSuccess(authProvider);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO show error message
                //System.out.println(error.toString());
            }
        });

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
        Intent registerIntent = new Intent(this, RegistrationActivity.class);
        LoginActivity.this.startActivity(registerIntent);
    }
}
