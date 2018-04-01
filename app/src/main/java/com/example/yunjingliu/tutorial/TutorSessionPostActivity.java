package com.example.yunjingliu.tutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zr.auth.JsonObjectAuthRequest;
import com.zr.forms.JsonForm;
import com.zr.forms.JsonFormErrorListener;
import com.zr.forms.SpinnerAdapter;

import org.json.JSONObject;

public class TutorSessionPostActivity extends AppCompatActivity {
    private final JsonForm form;
    // To match backend definitions
    // Note: consider using OPTION to retrieve this information.
    private static final String[] dayChoices = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    public TutorSessionPostActivity() {
        form = new JsonForm(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_session_post);
        Bundle msg = getIntent().getExtras();
        if(msg != null) {
           getProfile();
        }
        form.put("title", R.id.etTitle);
        form.put("description", R.id.etDescription);
        form.put("time", R.id.etTimechoose);
        form.put("place", R.id.etLocationinput);
        form.put("day", new SpinnerAdapter(findViewById(R.id.spDatechoose), dayChoices));

        final Spinner spinner = findViewById(R.id.spDatechoose);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.week_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


    }

    public void onClickSubmit(View view) {
        final MyApp app = (MyApp) getApplication();
        int method;
        String url;
        Bundle msg = getIntent().getExtras();
        if(msg != null) {
            method = Request.Method.PUT;
            url = msg.getString("url");
        }
        else{
            method = Request.Method.POST;
            url = Backend.url("/sessions/");
        }
        JsonObjectAuthRequest postSessionRequest = new JsonObjectAuthRequest(
                method,
                url,
                app.getAuthProvider(),
                form.getJson(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        finish();
                    }
                },
                new JsonFormErrorListener(form));
        RequestQueue queue = app.getRequestQueue();
        queue.add(postSessionRequest);

    }


    public void getProfile() {
        final MyApp app = (MyApp) getApplication();
        JsonObjectAuthRequest getDetailRequest = new JsonObjectAuthRequest(
                Request.Method.GET,
                getIntent().getStringExtra("url"),
                app.getAuthProvider(),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        form.setJson(response);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: show error message
                //System.out.println(error.toString());
            }
        });

        RequestQueue queue = app.getRequestQueue();
        queue.add(getDetailRequest);
    }
}