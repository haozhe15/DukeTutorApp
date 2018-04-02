package com.example.yunjingliu.tutorial;

import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by YunjingLiu on 3/31/18.
 */

public class SessionAppliedActivity extends JSonArrayReceiveActivity{
    ArrayList<Bundle> urls = new ArrayList<>();
    ArrayList<String> status = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_applied);
        getProfile(Request.Method.GET, Backend.url("/applications/"));


        ListView sessionList = (ListView) findViewById(R.id.lvSessionApplied);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, status);
        sessionList.setAdapter(adapter);

        sessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SessionAppliedActivity.this, SessionDetailActivity.class);
                intent.putExtras(urls.get(i));
                startActivity(intent);
            }
        });

    }



    public void onReceiveSessionList(JSONArray array) throws JSONException {
        //System.out.println("log in response: " + array.toString());
        // TODO: populate a list in UI
        //String msg = new String("Your Tutor Session: \n\n");
        status.clear();
        urls.clear();
        for (int i = 0; i < array.length(); i++) {
            JSONObject JObject = array.getJSONObject(i);
            System.out.println("JObject: " + JObject.toString());
            String acpt;
            if(JObject.isNull("accepted")){
                acpt = "Undecided";
            }
            else{
                acpt = JObject.getString("accepted");
            }
            status.add(acpt);
            String url = JObject.getString("url");
            Bundle temp = new Bundle();
            temp.putString("url", url);
            urls.add(temp);
        }
    }

}
