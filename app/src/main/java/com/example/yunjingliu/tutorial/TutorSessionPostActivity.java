package com.example.yunjingliu.tutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.zr.auth.JsonObjectAuthRequest;
import com.zr.forms.FormEntryAdapter;
import com.zr.forms.JsonForm;
import com.zr.forms.JsonFormErrorListener;

import org.json.JSONObject;

class DayPickerAdapter extends FormEntryAdapter {
    // To match backend definitions
    // Note: consider using OPTION to retrieve this information.
    private final static String[] choices = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    public DayPickerAdapter(Spinner spinner) {
        super(spinner);
    }

    @Override
    public String toString() {
        int i = ((Spinner) getView()).getSelectedItemPosition();
        return choices[i];
    }

    @Override
    public void setError(String error) {
        Spinner spinner = (Spinner) getView();
        ((TextView) spinner.getSelectedView()).setError(error);
    }
}

public class TutorSessionPostActivity extends AppCompatActivity {
    private final JsonForm form;

    public TutorSessionPostActivity() {
        form = new JsonForm(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_session_post);

        form.put("title", R.id.etTitle);
        form.put("description", R.id.etDescription);
        form.put("time", R.id.etTimechoose);
        form.put("place", R.id.etLocationinput);
        form.put("day", R.id.spDatechoose, DayPickerAdapter.class);

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

        JsonObjectAuthRequest postSessionRequest = new JsonObjectAuthRequest(
                Request.Method.POST,
                Backend.url("/sessions/"),
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
}