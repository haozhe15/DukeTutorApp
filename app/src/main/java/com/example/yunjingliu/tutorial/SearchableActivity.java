package com.example.yunjingliu.tutorial;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zr.auth.JsonArrayAuthRequest;
import com.zr.auth.JsonObjectAuthRequest;
import com.zr.forms.FormEntryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Haozhe Wang on 3/30/18.
 */
public class SearchableActivity extends AppCompatActivity {

    ArrayList<Bundle> urls = new ArrayList<>();
    ArrayList<String> items = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            // Do my search activity
            HashMap<String, String> params = new HashMap<>();
            params.put("keywords", query);
            JSONObject kwJSON =new JSONObject(params);
            getSearchResult(kwJSON);
        }
    }
//    public void getSearchResult(JSONObject kwJSON) {
//        final MyApp app = (MyApp) getApplication();
//        JsonArrayAuthRequest getSearchResult = new JsonArrayAuthRequest(
//                Request.Method.POST,
//                Backend.url("/search/"),
//                app.getAuthProvider(),
//                kwJSON,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        try {
//                            onReceiveSearchResult(response);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // TODO: show error message
//                //System.out.println(error.toString());
//            }
//        });

    //Temporally test getSearchResult function
    public void getSearchResult(JSONObject kwJSON) {
        final MyApp app = (MyApp) getApplication();
        JsonArrayAuthRequest getSearchResult = new JsonArrayAuthRequest(
                Request.Method.GET,
                Backend.url("/sessions/"),
                app.getAuthProvider(),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            onReceiveSearchResult(response);
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
        queue.add(getSearchResult);
    }

    public void onReceiveSearchResult(JSONArray array) throws JSONException {
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
        // Similar as in UserareaActivity.java
        ListView searchList = (ListView) findViewById(R.id.lvSearchResult);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        searchList.setAdapter(adapter);
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchableActivity.this, SessionDetailActivity.class);
                intent.putExtras(urls.get(i));
                startActivity(intent);
            }
        });
        // Stop the similarity
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
}
