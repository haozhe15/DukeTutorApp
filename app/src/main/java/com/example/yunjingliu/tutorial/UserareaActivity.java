package com.example.yunjingliu.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zr.auth.JsonArrayAuthRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Haozhe Wang on 3/4/18.
 */
public class UserareaActivity extends AppCompatActivity {

    ArrayList<Bundle> urls = new ArrayList<>();
    ArrayList<String> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userarea);
        // TODO welcome message is removed
        // we cannot rely on the username stored in MyApp.
        // instead, we should make a request to retrieve the
        // name
        getProfile();
        ListView sessionList = (ListView) findViewById(R.id.lvSessionList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        sessionList.setAdapter(adapter);

        sessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(UserareaActivity.this, SessionDetailActivity.class);
                intent.putExtras(urls.get(i));
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getProfile();
    }

    public void addNew(View view) {
        Intent intent = new Intent(this, TutorSessionPostActivity.class);
        //startActivity(intent);
        startActivityForResult(intent, 1);
    }

    public void skipToSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);

    }


    public void getProfile() {
        final MyApp app = (MyApp) getApplication();
        JsonArrayAuthRequest getProfileRequest = new JsonArrayAuthRequest(
                Request.Method.GET,
                Backend.url("/sessions/"),
                app.getAuthProvider(),
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
        });

        RequestQueue queue = app.getRequestQueue();
        queue.add(getProfileRequest);
    }

    public void onReceiveSessionList(JSONArray array) throws JSONException {
        //System.out.println("log in response: " + array.toString());
        // TODO: populate a list in UI
        //String msg = new String("Your Tutor Session: \n\n");
        items.clear();
        urls.clear();
        for (int i = 0; i < array.length(); i++) {
            JSONObject JObject = array.getJSONObject(i);
            System.out.println("JObject: " + JObject.toString());
            String title = JObject.getString("title");
            items.add(title);
            String url = JObject.getString("url");
            Bundle temp = new Bundle();
            temp.putString("url", url);
            urls.add(temp);
        }
    }





}


