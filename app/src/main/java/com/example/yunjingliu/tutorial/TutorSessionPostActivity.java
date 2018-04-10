package com.example.yunjingliu.tutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.yunjingliu.tutorial.helper_class.Backend;
import com.example.yunjingliu.tutorial.helper_class.ErrorListener;
import com.example.yunjingliu.tutorial.helper_class.MyApp;
import com.zr.auth.JsonObjectAuthRequest;
import com.zr.forms.JsonForm;
import com.zr.forms.SpinnerAdapter;

import org.json.JSONObject;

public class TutorSessionPostActivity extends AppCompatActivity {
    // To match backend definitions
    // Note: consider using OPTION to retrieve this information.
    private static final String[] dayChoices = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private final JsonForm form = new JsonForm(this, new ErrorListener(this));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_session_post);

        form.put("title", R.id.etTitle);
        form.put("description", R.id.etDescription);
        form.put("time", R.id.etTimechoose);
        form.put("place", R.id.etLocationinput);
        form.put("day", new SpinnerAdapter(findViewById(R.id.spDatechoose), dayChoices));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            form.setBundle(extras);
        }

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
        String url = getIntent().getStringExtra("url");
        if (url != null) {
            method = Request.Method.PUT;
        } else {
            method = Request.Method.POST;
            url = Backend.url("/sessions/");
        }
        app.addRequest(new JsonObjectAuthRequest(
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
                form));
    }
}