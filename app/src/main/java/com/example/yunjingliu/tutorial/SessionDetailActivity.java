package com.example.yunjingliu.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by YunjingLiu on 3/27/18.
 */

public class SessionDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_detail);
        TextView sessionDetail = (TextView) findViewById(R.id.tvSessionDetail);

        String title = getIntent().getExtras().getString("title");
        String description = getIntent().getExtras().getString("description");
        String day = getIntent().getExtras().getString("day");
        String time = getIntent().getExtras().getString("time");
        String place = getIntent().getExtras().getString("place");

        String msg = new String();
        msg= msg + "title: " + title + "\n" +
                    "description: " + description + "\n" +
                    "day: " + day + "\n" +
                    "time: " + time + "\n" +
                    "place: " + place + "\n" +
                    "\n";
        sessionDetail.setText(msg);
        //System.out.println("session detail : " + msg);
    }


    public void sessionEdit(View view) {
        Intent intent = new Intent(this, TutorSessionPostActivity.class);
        Bundle msg = getIntent().getExtras();
        intent.putExtras(msg);
        startActivity(intent);

    }
}
