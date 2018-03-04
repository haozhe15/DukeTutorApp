package com.example.yunjingliu.tutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

public class RegistrationActivity extends AppCompatActivity {
    EditText etUsername;
    EditText etFirstname;
    EditText etLastname;
    EditText etPassword;
    EditText etEmail;
    Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etFirstname = (EditText) findViewById(R.id.etFirstname);
        etLastname = (EditText) findViewById(R.id.etLastname);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);
        btRegister = (Button) findViewById(R.id.btRegister);

        btRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String username = etUsername.getText().toString();
                final String firstname = etFirstname.getText().toString();
                final String lastname = etLastname.getText().toString();
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent registerFinish = new Intent(RegistrationActivity.this, loginActivity.class);
                        RegistrationActivity.this.startActivity(registerFinish);
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(username,firstname,lastname,email,password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegistrationActivity.this);
                queue.add(registerRequest);
            }
        });

    }
}
