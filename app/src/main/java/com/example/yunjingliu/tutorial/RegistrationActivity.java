package com.example.yunjingliu.tutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

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

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String firstname = etFirstname.getText().toString();
                final String lastname = etLastname.getText().toString();
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("email", email);
                params.put("first_name", firstname);
                params.put("last_name", lastname);
                JsonObjectAuthRequest registerRequest = new JsonObjectAuthRequest(
                        Request.Method.POST, "http://vcm-3307.vm.duke.edu:8000/users/",
                        null,
                        new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        finish();
                        //Intent registerFinish = new Intent(RegistrationActivity.this, LoginActivity.class);
                        //RegistrationActivity.this.startActivity(registerFinish);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO show error message

                    }
                });
                RequestQueue queue = Volley.newRequestQueue(RegistrationActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
