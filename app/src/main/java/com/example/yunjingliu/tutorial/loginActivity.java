package com.example.yunjingliu.tutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import org.json.JSONObject;

import java.util.HashMap;

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
                JsonArrayAuthRequest registerRequest = new JsonArrayAuthRequest(
                        Request.Method.GET, "http://vcm-3307.vm.duke.edu:8000/users/",
                        etUsername.toString(),etPassword.toString(),
                        null,
                        new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response.toString());
                        Intent LoginIntent = new Intent(loginActivity.this, UserareaActivity.class);
                        loginActivity.this.startActivity(LoginIntent);
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO show error message
                        System.out.println(error.toString());
                    }
                }
                );

                RequestQueue queue = Volley.newRequestQueue(loginActivity.this);
                queue.add(registerRequest);



            }
        });



    }
}
