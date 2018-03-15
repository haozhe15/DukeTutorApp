package com.example.yunjingliu.tutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by Haozhe Wang on 3/4/18.
 */
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

    public void onClickRefresh(View view){
        getProfile();
    }

    public void getProfile(){
        String username;
        String password;
        Bundle b;
        b = ((myApp)getApplicationContext()).getInfo();
        username = b.getString("username");
        password = b.getString("password");

        JsonArrayAuthRequest getProfileRequest = new JsonArrayAuthRequest(
                Request.Method.GET, "http://vcm-3307.vm.duke.edu:8000/sessions/",
                username,password,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            onReceiveSessionList(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: show error message
                //System.out.println(error.toString());

            }
        }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getProfileRequest);

    }

    public void onReceiveSessionList(JSONArray array) throws JSONException {
        System.out.println("log in response: "+ array.toString());
        // TODO: populate a list in UI
        String msg=new String("Your Tutor Session: \n\n");
        for (int i = 0; i<array.length(); i++){
            JSONObject JObject = array.getJSONObject(i);
            System.out.println("JObject: "+JObject.toString());
            String title = JObject.getString("title");
            String description = JObject.getString("description");
            String day = JObject.getString("day");
            String time = JObject.getString("time");
            String place = JObject.getString("place");

            msg=msg+"title: "+title+"\n"+
                    "description: "+description+"\n"+
                    "day: "+day+"\n"+
                    "time: "+time+"\n"+
                    "place: "+place+"\n"+
                    "\n";
        }
        TextView sessionView = (TextView) findViewById(R.id.sessionView);
        sessionView.setText(msg);
    }
}
