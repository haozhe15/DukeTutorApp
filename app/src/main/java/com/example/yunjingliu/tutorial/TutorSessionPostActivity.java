package com.example.yunjingliu.tutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zr.auth.JsonObjectAuthRequest;

import org.json.JSONObject;

import java.util.HashMap;


public class TutorSessionPostActivity extends AppCompatActivity {

    EditText title;
    EditText description;
    Spinner spinner;
    EditText time;
    EditText location;
    final String[] dateChoices = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_session_post);

        spinner = (Spinner) findViewById(R.id.spDatechoose);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.week_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        title = (EditText) findViewById(R.id.etTitle);
        description = (EditText) findViewById(R.id.etDescription);
        time = (EditText) findViewById(R.id.etTimechoose);
        location = (EditText) findViewById(R.id.etLocationinput);
    }

    public void onClickSubmit(View view) {
        final MyApp app = (MyApp) getApplication();
        HashMap<String, String> params = new HashMap<>();
        params.put("title", title.getText().toString());
        params.put("description", description.getText().toString());
        params.put("time", time.getText().toString());
        params.put("place", location.getText().toString());

        int i = spinner.getSelectedItemPosition();
        String date = dateChoices[i];
        params.put("day", date);
        JsonObjectAuthRequest postSessionRequest = new JsonObjectAuthRequest(
                Request.Method.POST, "http://vcm-3307.vm.duke.edu:8000/sessions/",
                app.getAuthProvider(),
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue queue = app.getRequestQueue();
        queue.add(postSessionRequest);
    }
}