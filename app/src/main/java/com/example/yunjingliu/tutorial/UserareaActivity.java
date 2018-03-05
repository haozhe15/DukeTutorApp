package com.example.yunjingliu.tutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UserareaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userarea);
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
