package com.example.yunjingliu.tutorial.otherActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.yunjingliu.tutorial.R;
import com.example.yunjingliu.tutorial.helper_class.Backend;
import com.example.yunjingliu.tutorial.helper_class.ErrorListener;
import com.example.yunjingliu.tutorial.helper_class.MyApp;
import com.zr.auth.JsonObjectAuthRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Haozhe Wang on 04/08/18.
 */
public class FeedbackActivity extends AppCompatActivity {

    RatingBar simpleRatingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitivity_feedback);
        simpleRatingBar = findViewById(R.id.ratingBar); // initiate a rating bar
        simpleRatingBar.setRating((float) 3.5); // set default rating
    }

    public void onClickSubmit(View view) {
        final MyApp app = (MyApp) getApplication();

        String session_url = getIntent().getStringExtra("session_url");
        Float ratingNumber = simpleRatingBar.getRating(); // get rating number from a rating bar

        try {
            JSONObject json = new JSONObject();
            TextView textView = findViewById(R.id.fbContent);
            json.put("session",session_url);
            json.put("content", textView.getText().toString());
            json.put("rating", ratingNumber);

            app.addRequest(new JsonObjectAuthRequest(
                    Request.Method.POST,
                    Backend.url("/feedbacks/"),
                    app.getAuthProvider(),
                    json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            finish();
                        }
                    },
                    new ErrorListener(this)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
