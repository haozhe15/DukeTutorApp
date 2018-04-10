package com.example.yunjingliu.tutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

import com.example.yunjingliu.tutorial.helper_class.ErrorListener;
import com.zr.forms.JsonForm;

/**
 * Created by Haozhe Wang on 04/08/18.
 */
public class FeedbackActivity extends AppCompatActivity {
    private final JsonForm form;

    public FeedbackActivity() {
        form = new JsonForm(this, new ErrorListener(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.ratingBar); // initiate a rating bar
        simpleRatingBar.setRating((float) 3.5); // set default rating
        Float ratingNumber = simpleRatingBar.getRating(); // get rating number from a rating bar

    }

    public void onClickSubmit(View view){

    }
}
