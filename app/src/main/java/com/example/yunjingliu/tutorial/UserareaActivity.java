package com.example.yunjingliu.tutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserareaActivity extends AppCompatActivity {
    TextView welcomeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userarea);
        //print
        String username;
        Bundle b;
        welcomeUser = (TextView) findViewById(R.id.tvWelcome);
        b = ((myApp)getApplicationContext()).getInfo();
        username = b.getString("username");
        String msg = "Welcome! " +username;
        welcomeUser.setText(msg);
    }

    public void addNew(View view){
        Intent intent = new Intent(this, TutorSessionPostActivity.class);
        startActivity(intent);

    }

    public void skipToSearch(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);

    }
}
