package com.example.yunjingliu.tutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zr.auth.JsonObjectAuthRequest;

import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {
    private final JsonForm form;

    public RegistrationActivity() {
        form = new JsonForm(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        form.put("username", R.id.etUsername);
        form.put("password", R.id.etPassword);
        form.put("email", R.id.etEmail);
        form.put("first_name", R.id.etFirstname);
        form.put("last_name", R.id.etLastname);
    }

    public void onClickRegister(View view) {
        final MyApp app = (MyApp) getApplication();

        JsonObjectAuthRequest registerRequest = new JsonObjectAuthRequest(
                Request.Method.POST, "http://vcm-3307.vm.duke.edu:8000/users/",
                null,
                form.getJson(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        finish();
                    }
                },
                new JsonFormErrorListener(form));
        RequestQueue queue = app.getRequestQueue();
        queue.add(registerRequest);
    }
}
