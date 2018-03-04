package com.example.yunjingliu.tutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class loginActivity extends AppCompatActivity {
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
        btLogin = (Button) findViewById(R.id.btLogin );
        etRegisterLink = (TextView) findViewById(R.id.tvRegisterHere);
        etRegisterLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registerIntent = new Intent(loginActivity.this, RegistrationActivity.class);
                loginActivity.this.startActivity(registerIntent);
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent LoginIntent = new Intent(loginActivity.this, UserareaActivity.class);
                loginActivity.this.startActivity(LoginIntent);
            }
        });



    }
}
