package com.example.yunjingliu.tutorial;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class DukeTutor extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    selectedFragment = new SearchFragment();
                    //mTextMessage.setText(R.string.Title_search);
                    break;
                case R.id.navigation_message:
                    selectedFragment = new MessageFragment();
                    //mTextMessage.setText(R.string.Title_message);
                    break;
                case R.id.navigation_application:
                    selectedFragment = new ApplicationFragment();
                   // mTextMessage.setText(R.string.Title_application);
                    break;
                case R.id.navigation_me:
                    selectedFragment = new MeFragment();
                    //mTextMessage.setText(R.string.Title_me);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, selectedFragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new SearchFragment()).commit();
    }

}
