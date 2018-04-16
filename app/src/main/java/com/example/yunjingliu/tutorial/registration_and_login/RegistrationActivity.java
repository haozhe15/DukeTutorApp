package com.example.yunjingliu.tutorial.registration_and_login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.yunjingliu.tutorial.R;
import com.example.yunjingliu.tutorial.helper_class.Backend;
import com.example.yunjingliu.tutorial.helper_class.ErrorListener;
import com.example.yunjingliu.tutorial.helper_class.MyApp;
import com.zr.auth.JsonObjectAuthRequest;
import com.zr.forms.JsonForm;

import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {
    final JsonForm form = new JsonForm(this, new ErrorListener(this));
    EditText etUsername, etPassword, etEmail, etFirstname, etLastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etFirstname = findViewById(R.id.etFirstname);
        etLastname = findViewById(R.id.etLastname);

        form.put("username", R.id.etUsername);
        form.put("password", R.id.etPassword);
        form.put("email", R.id.etEmail);
        form.put("first_name", R.id.etFirstname);
        form.put("last_name", R.id.etLastname);
    }

    public void onClickRegister(View view) {
        final MyApp app = (MyApp) getApplication();
        if (validate()) { // validate the format of input information
            app.addRequest(new JsonObjectAuthRequest(
                    Request.Method.POST,
                    Backend.url("/users/"),
                    null,
                    form.getJson(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            finish();
                        }
                    },
                    form));
        }
        else {
            // Registration failed.
            Toast.makeText(this, "Registration has failed!",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validate(){
        boolean valid = true;
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String first_name = etFirstname.getText().toString().trim();
        String last_name = etLastname.getText().toString().trim();

        if (username.isEmpty() || username.length()>32){
            etUsername.setError("Please enter valid name!");
            valid = false;
        }
        if (password.isEmpty() || password.length()<8){
            etPassword.setError("Please enter valid password longer than 8 letters!");
            valid = false;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please enter valid email address!");
            valid = false;
        }
        if (first_name.isEmpty() || first_name.length()>32){
            etFirstname.setError("Please enter valid first name!");
            valid = false;
        }
        if (last_name.isEmpty() || last_name.length()>32){
            etLastname.setError("Please enter valid last name!");
            valid = false;
        }
        return valid;
    }
}
