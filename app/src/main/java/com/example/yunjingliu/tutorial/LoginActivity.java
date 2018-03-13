package com.example.yunjingliu.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername;
    EditText etPassword;
    Button btLogin;
    TextView etRegisterLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
        etRegisterLink = (TextView) findViewById(R.id.tvRegisterHere);

        etRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final BasicAuthProvider authProvider = new BasicAuthProvider();
                authProvider.setUsernamePassword(username, password);
                JsonArrayAuthRequest registerRequest = new JsonArrayAuthRequest(
                        Request.Method.GET, "http://vcm-3307.vm.duke.edu:8000/users/",
                        authProvider,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                LoginActivity.this.onLoginSuccess(authProvider);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO show error message
                        //System.out.println(error.toString());
                    }
                });

                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(registerRequest);
            }
        });
    }

    public void onLoginSuccess(AuthProvider authProvider) {
        MyApp app = (MyApp) getApplication();
        app.setAuthProvider(authProvider);
        Intent LoginIntent = new Intent(this, UserareaActivity.class);
        startActivity(LoginIntent);
    }
}
