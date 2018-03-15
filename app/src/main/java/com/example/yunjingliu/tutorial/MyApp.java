package com.example.yunjingliu.tutorial;

import android.app.Application;
import android.os.Bundle;

/**
 * Created by YunjingLiu on 3/4/18.
 */

public class MyApp extends Application{
    String usname;
    String pwd;

    public Bundle getInfo(){
        Bundle b = new Bundle();
        b.putString("username",usname);
        b.putString("password",pwd);
        return b;
    }
    public void setInfo(String username, String password){
        usname = username;
        pwd = password;
    }
}
